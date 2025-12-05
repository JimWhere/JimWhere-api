package com.jimwhere.api.user.controller;

import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.user.dto.reqeust.UserCreateRequest;
import com.jimwhere.api.user.dto.reqeust.UserUpdatePhoneRequest;
import com.jimwhere.api.user.dto.reqeust.UserUpdateRequest;
import com.jimwhere.api.user.dto.response.UserResponse;
import com.jimwhere.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> registUser(@RequestBody UserCreateRequest request){

        userService.createUser(request);

        return ResponseEntity.ok("회원가입 완료!");
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){

        List<UserResponse> response = userService.findAll();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/users/me")
    public ResponseEntity<UserResponse> getMyUserInfo(@AuthenticationPrincipal CustomUser customUser){

        String username = customUser.getUsername();

        UserResponse response = userService.findMyInfo(username);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userCode}")
    public ResponseEntity<UserResponse> getUserByUserCode(@PathVariable long userCode){

        UserResponse response = userService.findUserByCode(userCode);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/user/users/modify")
    public ResponseEntity<String> modifyUserPhoneNumber(
            @AuthenticationPrincipal CustomUser customUser
            , @RequestBody UserUpdatePhoneRequest request){

        String username = customUser.getUsername();
        userService.updateUserPhoneNumber(username , request);



        return ResponseEntity.ok("전화번호 수정 완료!");
    }

    @DeleteMapping("/user/users/withdraw")
    public ResponseEntity<String> leaveUser( @AuthenticationPrincipal CustomUser customUser ){
        String username = customUser.getUsername();
        userService.deactivateUser(username);

        return ResponseEntity.ok("회원 탈퇴 성공!");
    }

    @PatchMapping("/admin/users/modify/{userCode}")
    public ResponseEntity<String> modifyUserByCode(
            @PathVariable long userCode ,
            @RequestBody UserUpdateRequest request){

        userService.updateUserAdminSettings(userCode,request);

        return ResponseEntity.ok("회원 정보 수정 완료!");
    }


}
