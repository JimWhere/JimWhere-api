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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="문의사항 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    //문의 사항 생성
  @Operation(
      summary = "문의사항 생성", description = "유저가 관리자에게 문의할 사항을 생성하는 api"
  )@PostMapping("/user/inquiry")
    public ResponseEntity<ApiResponse<String>> createInquiry(
            @RequestBody CreateInquiryRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        String userName = user.getUsername();
        return ResponseEntity.ok(ApiResponse.success(inquiryService.createInquiry(request, userName)));
    }

    //문의 사항 삭제
  @Operation(
      summary = "문의사항 삭제", description = "관리자가 문의사항을 삭제하는 api"
  )
    @DeleteMapping("/admin/inquiry/{inquiryCode}")
    public ResponseEntity<ApiResponse<String>> deleteInquiry(
            @PathVariable Long inquiryCode
    ) {
        return ResponseEntity.ok(ApiResponse.success(inquiryService.deleteInquiry(inquiryCode)));
    }

    //관리자 문의 답변
  @Operation(
      summary = "관리자 문의 답변", description = "관리자가 문의에 대한 답변을 작성하는 api"
  )
    @PutMapping("/admin/inquiry/{inquiryCode}")
    public ResponseEntity<ApiResponse<String>> updateInquiryAnswer(
            @PathVariable Long inquiryCode,
            @RequestBody UpdateAnswerRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        String userName = user.getUsername();
        return ResponseEntity.ok(ApiResponse.success(inquiryService.updateAnswer(inquiryCode, request, userName)));
    }

    //문의사항 상세 조회
  @Operation(
      summary = "문의사항 상세 조회", description = "관리자와 유저가 문의의 사항을 상세 조회하는 api"
  )
    @GetMapping("/inquiry/{inquiryCode}")
    public ResponseEntity<ApiResponse<InquiryResponse>> getInquiry(
            @PathVariable Long inquiryCode
    ) {
        return ResponseEntity.ok(ApiResponse.success(inquiryService.getInquiry(inquiryCode)));
    }

    //유저 문의 조회
    @Operation(
        summary = "유저 문의 조회", description = "유저가 본인이 작성한 문의사항을 조회하는 api"
    )
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
  @Operation(
      summary = "관리자 문의사항 조회", description = "관리자가 전체 문의사항을 조회하는 api"
  )
    @GetMapping("/admin/inquiry") //리스트 조회
    public ApiResponse<PageResponse<InquiryListResponse>> getInquiryListAll(@PageableDefault Pageable pageable) {
        Page<InquiryListResponse> inquiryList = inquiryService.getInquiryListAll(pageable);
        return ApiResponse.success(PageResponse.of(inquiryList));
    }

    // 관리자 대시보드용
    @Operation(
        summary = "문의사항(관리자 대시보드용)", description = "관리자 대시보드에 출력할 api"
    )
    @GetMapping("/admin/inquiry/stats")
    public ApiResponse<InquiryStatsResponse> getInquiryStats() {
        InquiryStatsResponse stats = inquiryService.getInquiryStats();
        return ApiResponse.success(stats);
    }
}
