package com.jimwhere.api.user.controller;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.user.dto.reqeust.UserUpdatePhoneRequest;
import com.jimwhere.api.user.dto.reqeust.UserUpdateRequest;
import com.jimwhere.api.user.dto.response.UserResponse;
import com.jimwhere.api.user.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 관리자: 전체 유저 조회
    @GetMapping("/admin/users")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(Pageable pageable) {
        PageResponse<UserResponse> response = userService.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 사용자: 내 정보 조회
    @GetMapping("/user/users/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyUserInfo(
            @AuthenticationPrincipal CustomUser customUser) {

        if (customUser == null) {
            throw new CustomException(ErrorCode.NULL_UNAUTHORIZED);
        }

        String username = customUser.getUsername();
        UserResponse response = userService.findMyInfo(username);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 특정 유저 조회
    @GetMapping("/users/{userCode}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUserCode(@PathVariable long userCode) {

        UserResponse response = userService.findUserByCode(userCode);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 사용자 전화번호 수정
    @PatchMapping("/user/users/modify")
    public ResponseEntity<ApiResponse<String>> modifyUserPhoneNumber(
            @AuthenticationPrincipal CustomUser customUser,
            @RequestBody UserUpdatePhoneRequest request) {

        String username = customUser.getUsername();
        userService.updateUserPhoneNumber(username, request);

        return ResponseEntity.ok(ApiResponse.success("전화번호 수정 완료!"));
    }

    // 사용자 탈퇴
    @DeleteMapping("/user/users/withdraw")
    public ResponseEntity<ApiResponse<String>> leaveUser(
            @AuthenticationPrincipal CustomUser customUser) {

        String username = customUser.getUsername();
        userService.deactivateUser(username);

        return ResponseEntity.ok(ApiResponse.success("회원 탈퇴 성공!"));
    }

    // 관리자 특정 유저 정보 수정
    @PatchMapping("/admin/users/modify/{userCode}")
    public ResponseEntity<ApiResponse<String>> modifyUserByCode(
            @PathVariable long userCode,
            @RequestBody UserUpdateRequest request) {

        userService.updateUserAdminSettings(userCode, request);

        return ResponseEntity.ok(ApiResponse.success("회원 정보 수정 완료!"));
    }
}
