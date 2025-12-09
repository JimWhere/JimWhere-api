package com.jimwhere.api.auth.controller;

import com.jimwhere.api.auth.service.SmsService;
import com.jimwhere.api.global.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    // 인증번호 요청
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> sendCode(@RequestBody Map<String, String> request) {
        smsService.sendVerificationCode(request.get("phone"));
        return ResponseEntity.ok(ApiResponse.success("인증번호 발송 완료"));
    }

    // 인증번호 검증
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyCode(@RequestBody Map<String, String> request) {
        boolean result = smsService.verifyCode(
                request.get("phone"),
                request.get("code")
        );

        return ResponseEntity.ok(ApiResponse.success(result));

    }
}
