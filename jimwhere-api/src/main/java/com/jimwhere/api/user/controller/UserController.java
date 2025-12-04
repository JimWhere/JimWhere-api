package com.jimwhere.api.user.controller;

import com.jimwhere.api.user.dto.reqeust.UserCreateRequest;
import com.jimwhere.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
