package com.jimwhere.api.payment.controller;

import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.payment.dto.request.TossConfirmRequest;
import com.jimwhere.api.payment.dto.request.TossInitRequest;
import com.jimwhere.api.payment.dto.response.TossConfirmResponse;
import com.jimwhere.api.payment.dto.response.TossInitResponse;
import com.jimwhere.api.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    // 결제 준비
    // 유저 토큰 배제

    @PostMapping("/init")
    public ApiResponse<TossInitResponse> initPayment(@RequestBody TossInitRequest request) {
        TossInitResponse response = paymentService.initPayment(request);
        return ApiResponse.success(response);
    }

    // 결제 성공 후 confirm
    @PostMapping("/success")
    public ApiResponse<TossConfirmResponse> confirmPayment(@RequestBody TossConfirmRequest request) {
        TossConfirmResponse response = paymentService.confirmPayment(request);
        return ApiResponse.success(response);
    }
    // 실패 콜백
    @GetMapping("/fail")
    public ApiResponse<String> failPayment(@RequestParam String code,
                                           @RequestParam String message) {
        return ApiResponse.failure(code, "결제 실패 : " + message);
    }
}
