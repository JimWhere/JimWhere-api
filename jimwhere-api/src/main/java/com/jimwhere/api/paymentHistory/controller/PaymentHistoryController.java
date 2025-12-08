package com.jimwhere.api.paymentHistory.controller;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.paymentHistory.dto.response.PaymentHistoryResponse;
import com.jimwhere.api.paymentHistory.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    @GetMapping("/user/paymentHistories")
    public PageResponse<PaymentHistoryResponse> getMyPaymentHistories(
            @AuthenticationPrincipal CustomUser user,
            @PageableDefault Pageable pageable
    ) {
        String username = user.getUsername();
        Page<PaymentHistoryResponse> page = paymentHistoryService.getMyPaymentHistories(username, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/user/paymentHistories/{paymentHistoryCode}")
    public ResponseEntity<PaymentHistoryResponse> getMyPaymentHistoryDetail(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable Long paymentHistoryCode
    ) {
        String username = user.getUsername();
        return ResponseEntity.ok(
                paymentHistoryService.getMyPaymentHistoryDetail(username, paymentHistoryCode)
        );
    }

    @GetMapping("/admin/paymentHistories")
    public PageResponse<PaymentHistoryResponse> getPaymentHistoriesForAdmin(
            @PageableDefault Pageable pageable
    ) {
        Page<PaymentHistoryResponse> page = paymentHistoryService.getPaymentHistoriesForAdmin(pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/admin/paymentHistories/{paymentHistoryCode}")
    public ResponseEntity<PaymentHistoryResponse> getPaymentHistoryDetailForAdmin(
            @PathVariable Long paymentHistoryCode
    ) {
        return ResponseEntity.ok(
                paymentHistoryService.getPaymentHistoryDetailForAdmin(paymentHistoryCode)
        );
    }
}
