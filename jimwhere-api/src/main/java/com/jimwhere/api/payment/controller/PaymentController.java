package com.jimwhere.api.payment.controller;
import com.jimwhere.api.global.config.security.CustomUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PostMapping("/init")
    public ApiResponse<TossInitResponse> initPayment(
            @RequestHeader("Origin") String origin,
            @RequestBody TossInitRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        String username = user.getUsername();  // ← userId
        TossInitResponse response = paymentService.initPayment(username, request);
        return ApiResponse.success(response);
    }

    //  결제 확정
    @PostMapping("/success")
    public ApiResponse<TossConfirmResponse> confirmPayment(
            @RequestBody TossConfirmRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        String username = user.getUsername();  // ← userId
        TossConfirmResponse response = paymentService.confirmPayment(username, request);
        return ApiResponse.success(response);
    }
    // 실패 콜백
    @GetMapping("/fail")
    public ApiResponse<String> failPayment(@RequestParam String code,
                                           @RequestParam String message) {
        return ApiResponse.failure(code, "결제 실패 : " + message);
    }
}
