package com.jimwhere.api.inquiry.controller;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.inquiry.dto.request.CreateInquiryRequest;
import com.jimwhere.api.inquiry.dto.response.InquiryListResponse;
import com.jimwhere.api.inquiry.dto.response.InquiryResponse;
import com.jimwhere.api.inquiry.dto.request.UpdateAnswerRequest;
import com.jimwhere.api.inquiry.dto.response.InquiryStatsResponse;
import com.jimwhere.api.inquiry.sevice.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping("/user/inquiry")
    public ResponseEntity<ApiResponse<String>> createInquiry(
            @RequestBody CreateInquiryRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        String userName = user.getUsername();
        return ResponseEntity.ok(ApiResponse.success(inquiryService.createInquiry(request, userName)));
    }

    @DeleteMapping("/admin/inquiry/{inquiryCode}")
    public ResponseEntity<ApiResponse<String>> deleteInquiry(
            @PathVariable Long inquiryCode
    ) {
        return ResponseEntity.ok(ApiResponse.success(inquiryService.deleteInquiry(inquiryCode)));
    }

    @PutMapping("/admin/inquiry/{inquiryCode}")
    public ResponseEntity<ApiResponse<String>> updateInquiryAnswer(
            @PathVariable Long inquiryCode,
            @RequestBody UpdateAnswerRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        String userName = user.getUsername();
        return ResponseEntity.ok(ApiResponse.success(inquiryService.updateAnswer(inquiryCode, request, userName)));
    }

    @GetMapping("/inquiry/{inquiryCode}")
    public ResponseEntity<ApiResponse<InquiryResponse>> getInquiry(
            @PathVariable Long inquiryCode
    ) {
        return ResponseEntity.ok(ApiResponse.success(inquiryService.getInquiry(inquiryCode)));
    }

    @GetMapping("/user/inquiry")
    public ApiResponse<PageResponse<InquiryListResponse>> getInquiryList(
            @PageableDefault Pageable pageable,
            @AuthenticationPrincipal CustomUser user
    ) {
        String userName = user.getUsername();
        Page<InquiryListResponse> inquiryList = inquiryService.getInquiryList(pageable,
                userName);
        return ApiResponse.success(PageResponse.of(inquiryList));
    }

    @GetMapping("/admin/inquiry") //리스트 조회
    public ApiResponse<PageResponse<InquiryListResponse>> getInquiryListAll(@PageableDefault Pageable pageable) {
        Page<InquiryListResponse> inquiryList = inquiryService.getInquiryListAll(pageable);
        return ApiResponse.success(PageResponse.of(inquiryList));
    }

    // 관리자 대시보드용
    @GetMapping("/admin/inquiry/stats")
    public ApiResponse<InquiryStatsResponse> getInquiryStats() {
        InquiryStatsResponse stats = inquiryService.getInquiryStats();
        return ApiResponse.success(stats);
    }
}
