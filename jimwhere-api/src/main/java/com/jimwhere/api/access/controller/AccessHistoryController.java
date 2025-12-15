package com.jimwhere.api.access.controller;


import com.jimwhere.api.access.dto.request.CreateAccessHistoryRequest;
import com.jimwhere.api.access.dto.response.AccessHistoryAllResponse;
import com.jimwhere.api.access.dto.response.AccessHistoryDetailResponse;
import com.jimwhere.api.access.dto.response.AccessHistoryResponse;
import com.jimwhere.api.access.service.AccessHistoryService;
import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.user.dto.response.QrIssueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "출입 내역 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccessHistoryController {

  private final AccessHistoryService accessHistoryService;


  @Operation(
      summary = "유저 출입 신청", description = "유저가 대여공간 진입 전 출입을 신청한다 "
  )
  @PostMapping("/user/access/history")
  public ResponseEntity<ApiResponse<QrIssueResponse>> createAccessHistory(
      @RequestBody CreateAccessHistoryRequest request,
      @AuthenticationPrincipal CustomUser user
  ) {
    String userName = user.getUsername();
    QrIssueResponse response = accessHistoryService.createAccessHistory(request, userName);

    return ResponseEntity.ok(ApiResponse.success(response));
  }


  @Operation(
      summary = "출입내역 수정", description = "유저가 출입내역을 수정하는 api"
  )
  @PutMapping("/user/access/history/{accessHistoryCode}")
  public ResponseEntity<ApiResponse<QrIssueResponse>> updateAccessHistory(
      @AuthenticationPrincipal CustomUser user,
      @RequestBody CreateAccessHistoryRequest request,
      @PathVariable Long accessHistoryCode
  ) {
    String userName = user.getUsername();
    QrIssueResponse response = accessHistoryService.updateAccessHistory(request, userName,accessHistoryCode);
    return ResponseEntity.ok(ApiResponse.success(response));
  }


  @Operation(
      summary = "유저 출입내역 리스트 조회", description = "유저가 본인의 출입내역 리스트를 조회하는 api"
  )
  @GetMapping("/user/access/history")
  public ApiResponse<PageResponse<AccessHistoryResponse>> getAccessList(@PageableDefault Pageable pageable,
      @AuthenticationPrincipal CustomUser user) {
      String userName = user.getUsername();
    Page<AccessHistoryResponse> accessHistoryList=accessHistoryService.getAccessHistoryList(pageable,userName);
    return ApiResponse.success(PageResponse.of(accessHistoryList));
  }


  @Operation(
      summary = "관리자 출입내역 조회", description = "관리자가 전체 유저의 출입내역 리스트를 조회하는 api"
  )
  @GetMapping("/admin/access/history")
  public  ApiResponse<PageResponse<AccessHistoryAllResponse>>getAccessListAll(@PageableDefault Pageable pageable,
      @AuthenticationPrincipal CustomUser user) {
    Page<AccessHistoryAllResponse> accessHistoryList=accessHistoryService.getAccessHistoryListAll(pageable);
    return ApiResponse.success(PageResponse.of(accessHistoryList));
  }


  @Operation(
      summary = "출입내역 상세 조회", description = "관리자와 유저가 특정 출입기록을 상세 조회하는 api"
  )
  @GetMapping("/access/history/{accessHistoryCode}")
  public ResponseEntity<ApiResponse<AccessHistoryDetailResponse>> selectAccessHistoryByCode(
      @PathVariable Long accessHistoryCode
  ) {
    AccessHistoryDetailResponse detail = accessHistoryService.selectDetailByCode(accessHistoryCode);

    return ResponseEntity.ok(ApiResponse.success(detail));
  }
}


