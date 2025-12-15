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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Tag(name="유저 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 관리자: 전체 유저 조회
    @Operation(
        summary = "관리자 전체 유저 리스트 조회", description = "관리자가 전체 유저의 리스트를 조회하는 api"
    )
    @GetMapping("/admin/users")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(
            @RequestParam(required = false) String status,
            Pageable pageable
    ) {
        PageResponse<UserResponse> response = userService.findAll(pageable, status);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

  @Operation(
      summary = "관리자 유저 조회(대시보드용)", description = "관리자 대시보드에 표기될 유저정보를 조회하는 api"
  )
    @GetMapping("/admin/users/stat")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getUserStatistics() {

        Map<String, Long> stats = userService.getUserStatistics();

        return ResponseEntity.ok(ApiResponse.success(stats));
    }


    // 사용자: 내 정보 조회
    @Operation(
        summary = "유저 내 정보 상세 조회", description = "유저가 본인의 상세 정보를 조회하는 api"
    )
    @GetMapping("/user/users/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyUserInfo(
            @AuthenticationPrincipal CustomUser customUser) {

        if (customUser == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        String username = customUser.getUsername();
        UserResponse response = userService.findMyInfo(username);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 특정 유저 조회
    @Operation(
        summary = "특정 유저 조회", description = "특정유저의 상세정보를 조회하는 api"
    )
    @GetMapping("/users/{userCode}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUserCode(@PathVariable long userCode) {

        UserResponse response = userService.findUserByCode(userCode);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 사용자 전화번호 수정
    @Operation(
        summary = "유저 전화번호 수정", description = "유저가 본인의 전화번호 정보를 수정하는 api"
    )
    @PatchMapping("/user/users/modify")
    public ResponseEntity<ApiResponse<String>> modifyUserPhoneNumber(
            @AuthenticationPrincipal CustomUser customUser,
            @RequestBody UserUpdatePhoneRequest request) {

        String username = customUser.getUsername();
        userService.updateUserPhoneNumber(username, request);

        return ResponseEntity.ok(ApiResponse.success("전화번호 수정 완료!"));
    }

    // 사용자 탈퇴
    @Operation(
        summary = "유저 탈퇴", description = "유저가 회원 탈퇴하는 api"
    )
    @DeleteMapping("/user/users/withdraw")
    public ResponseEntity<ApiResponse<String>> leaveUser(
            @AuthenticationPrincipal CustomUser customUser) {

        String username = customUser.getUsername();
        userService.deactivateUser(username);

        return ResponseEntity.ok(ApiResponse.success("회원 탈퇴 성공!"));
    }

    // 관리자 특정 유저 정보 수정
    @Operation(
        summary = "관리자 유저 특정 정보 수정", description = "관리자가 유저의 특정 정보를 수정하는 api"
    )
    @PatchMapping("/admin/users/modify/{userCode}")
    public ResponseEntity<ApiResponse<String>> modifyUserByCode(
            @PathVariable long userCode,
            @RequestBody UserUpdateRequest request) {

        userService.updateUserAdminSettings(userCode, request);

        return ResponseEntity.ok(ApiResponse.success("회원 정보 수정 완료!"));
    }
}
