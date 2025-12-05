package com.jimwhere.api.user.controller;

import com.jimwhere.api.user.dto.reqeust.UserCreateRequest;
import com.jimwhere.api.user.dto.response.UserResponse;
import com.jimwhere.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
