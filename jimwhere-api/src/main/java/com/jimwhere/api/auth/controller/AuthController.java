package com.jimwhere.api.auth.controller;

import com.jimwhere.api.auth.dto.LoginRequest;
import com.jimwhere.api.auth.model.TokenResponse;
import com.jimwhere.api.auth.service.BusinessNumberService;
import com.jimwhere.api.auth.service.UserAuthService;
import com.jimwhere.api.global.config.jwt.JwtTokenProvider;
import com.jimwhere.api.global.config.jwt.RefreshTokenService;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.dto.reqeust.UserCreateRequest;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService redisRefreshTokenService;
    private final UserAuthService userAuthService;

    private static final String COOKIE_NAME = "refreshToken";
    private final long REFRESH_TOKEN_EXPIRE = 1000 * 60 * 60; // 1시간

    @PostMapping("/signup")
    public ResponseEntity<String> registUser(@RequestBody UserCreateRequest request){

        userAuthService.createUser(request);

        return ResponseEntity.ok("회원가입 완료!");
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @RequestBody LoginRequest request
    ) {
        String userId = request.getUserId();
        String password = request.getPassword();

        User user = userRepository.findByUserId(userId).orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure(
                            ErrorCode.INVALID_USER_ID.getCode(),
                            ErrorCode.INVALID_USER_ID.getMessage()
                    ));
        }

        String role = user.getRole().name();

        String accessToken = jwtTokenProvider.createAccessToken(userId, role);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId, role);

        // refreshToken을 Redis에 저장(회전 / 검증용)
        redisRefreshTokenService.save(userId, refreshToken, REFRESH_TOKEN_EXPIRE);

        // 1) refreshToken을 HttpOnly 쿠키에 넣기
        ResponseCookie refreshCookie = ResponseCookie.from(COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(false)  // 개발 환경: false, 배포시 true + https
                .path("/")
                .maxAge(REFRESH_TOKEN_EXPIRE / 1000) // 초 단위
                .sameSite("Lax")
                .build();

        // 2) body에는 accessToken만 내려주기
        TokenResponse body = TokenResponse.builder()
                .accessToken(accessToken)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ApiResponse.success(body));
    }




    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal UserDetails userDetails,
            @CookieValue(name = COOKIE_NAME, required = false) String refreshToken
    ) {
        String userEmail = userDetails.getUsername();

        // Redis에서 refreshToken 삭제 (있으면)
        if (refreshToken != null && !refreshToken.isBlank()) {
            redisRefreshTokenService.delete(userEmail);
        }

        // 쿠키 즉시 만료
        ResponseCookie deleteCookie = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(ApiResponse.success(null));
    }


    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            @CookieValue(name = COOKIE_NAME, required = false) String refreshToken
    ) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure("AUTH-001", "토큰이 없어요"));
        }

        // refreshToken에서 userEmail 추출 (토큰 페이로드에 이메일 넣어뒀다는 전제)
        String userId = jwtTokenProvider.getUsername(refreshToken);

        // Redis에 저장된 refreshToken과 일치하는지 검증 + 토큰 자체 유효성도 검증
        if (!redisRefreshTokenService.isValid(userId, refreshToken)
                || !jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure("AUTH-002", "토큰이 안맞아요"));
        }

        // 새 토큰 생성
        String role = jwtTokenProvider.getRole(refreshToken);
        String newAccessToken = jwtTokenProvider.createAccessToken(userId, role);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId, role);

        // Redis에 새 refreshToken으로 교체(회전)
        redisRefreshTokenService.save(userId, newRefreshToken, REFRESH_TOKEN_EXPIRE);

        // 새 refreshToken을 다시 쿠키에 세팅
        ResponseCookie refreshCookie = ResponseCookie.from(COOKIE_NAME, newRefreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(REFRESH_TOKEN_EXPIRE / 1000)
                .sameSite("Lax")
                .build();

        TokenResponse body = TokenResponse.builder()
                .accessToken(newAccessToken)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ApiResponse.success(body));
    }



}
