package com.jimwhere.api.global.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jimwhere.api.global.config.security.CustomUserDetailsService;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.global.model.ApiResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    // 필요없음 시큐리티에서 걸러짐
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getRequestURI();
//
//        // 토큰 필요 없는 경로
//        return path.startsWith("/api/v1/auth/")
//                || path.startsWith("/swagger")
//                || path.startsWith("/v3/api-docs");
//    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

                String token = authHeader.substring(7);

                // 토큰 검증 (예외 발생 시 catch로 감)
                jwtTokenProvider.validateTokenOrThrow(token);

                // username 추출
                String username = jwtTokenProvider.getUsername(token);

                var userDetails = customUserDetailsService.loadUserByUsername(username);

                var authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            setErrorResponse(response, ErrorCode.INVALID_OR_EXPIRED_QR, "토큰이 만료되었습니다.");

        } catch (SecurityException e) {  // 시그니처 불일치, 변조됨
            setErrorResponse(response, ErrorCode.INVALID_REQUEST, "서명이 올바르지 않습니다.");

        } catch (MalformedJwtException e) {  // 형식이 잘못된 토큰
            setErrorResponse(response, ErrorCode.INVALID_REQUEST, "유효하지 않은 토큰 형식입니다.");

        } catch (JwtException e) {   // 기타 모든 JWT 오류
            setErrorResponse(response, ErrorCode.INVALID_REQUEST, "잘못된 JWT 토큰입니다.");

        }
    }

    private void setErrorResponse(HttpServletResponse response,
                                  ErrorCode errorCode,
                                  String customMessage) throws IOException {

        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType("application/json;charset=UTF-8");

        ApiResponse<?> api = ApiResponse.failure(errorCode.name(), customMessage);

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        response.getWriter().write(om.writeValueAsString(api));
    }
}
