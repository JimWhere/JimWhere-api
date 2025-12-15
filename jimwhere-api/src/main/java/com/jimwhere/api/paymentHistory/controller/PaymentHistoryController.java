package com.jimwhere.api.paymentHistory.controller;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.paymentHistory.dto.response.PaymentHistoryResponse;
import com.jimwhere.api.paymentHistory.service.PaymentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name="결제 내역 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    // USER — 목록
    @Operation(
        summary = "유저 결제 리스트 조회", description = "유저가 본인의 결제 리스트를 조회하는 api"
    )
    @GetMapping("/user/paymentHistories")
    public ApiResponse<PageResponse<PaymentHistoryResponse>> getMyPaymentHistories(
            @AuthenticationPrincipal CustomUser user,
            @PageableDefault Pageable pageable
    ) {
        String username = user.getUsername();
        Page<PaymentHistoryResponse> page =
                paymentHistoryService.getMyPaymentHistories(username, pageable);

        return ApiResponse.success(PageResponse.of(page));
    }

    // USER — 상세
    @Operation(
        summary = "유저 결제 상세 조회", description = "유저가 본인의 특정 결제를 상세조회 하는 api "
    )
    @GetMapping("/user/paymentHistories/{paymentHistoryCode}")
    public ApiResponse<PaymentHistoryResponse> getMyPaymentHistoryDetail(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable Long paymentHistoryCode
    ) {
        String username = user.getUsername();
        PaymentHistoryResponse response =
                paymentHistoryService.getMyPaymentHistoryDetail(username, paymentHistoryCode);

        return ApiResponse.success(response);
    }

    // ADMIN — 목록
    @Operation(
        summary = "관리자 결제 리스트 조회", description = "관리자가 전체 유저의 결제 리스트를 조회하는 api "
    )
    @GetMapping("/admin/paymentHistories")
    public ApiResponse<PageResponse<PaymentHistoryResponse>> getPaymentHistoriesForAdmin(
            @PageableDefault Pageable pageable
    ) {
        Page<PaymentHistoryResponse> page =
                paymentHistoryService.getPaymentHistoriesForAdmin(pageable);

        return ApiResponse.success(PageResponse.of(page));
    }

    // ADMIN 상세
    @Operation(
        summary = "관리자 결제 상세 조회", description = "관리자가 특정 결제를 상세 조회하는 api "
    )
    @GetMapping("/admin/paymentHistories/{paymentHistoryCode}")
    public ApiResponse<PaymentHistoryResponse> getPaymentHistoryDetailForAdmin(
            @PathVariable Long paymentHistoryCode
    ) {
        PaymentHistoryResponse response =
                paymentHistoryService.getPaymentHistoryDetailForAdmin(paymentHistoryCode);

        return ApiResponse.success(response);
    }
}
