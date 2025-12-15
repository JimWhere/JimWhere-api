package com.jimwhere.api.auth.controller;

import com.jimwhere.api.auth.service.SmsService;
import com.jimwhere.api.global.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@Tag(name="SMS API")
@RestController
@RequestMapping("/api/v1/auth/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    // 인증번호 요청
    @Operation(
        summary = "인증 번호 요청", description = "유저가 인증번호를 요청하는 api"
    )
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> sendCode(@RequestBody Map<String, String> request) {
        smsService.sendVerificationCode(request.get("phone"));
        return ResponseEntity.ok(ApiResponse.success("인증번호 발송 완료"));
    }

    // 인증번호 검증
    @Operation(
        summary = "인증번호 검증", description = "유저가 입력한 인증번호가 옳바른지 검증하는 api"
    )
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyCode(@RequestBody Map<String, String> request) {
        boolean result = smsService.verifyCode(
                request.get("phone"),
                request.get("code")
        );

        return ResponseEntity.ok(ApiResponse.success(result));

    }
}
