package com.jimwhere.api.access.controller;


import com.jimwhere.api.access.dto.request.CreateAccessHistoryRequest;
import com.jimwhere.api.access.dto.response.AccessHistoryDetailResponse;
import com.jimwhere.api.access.dto.response.AccessHistoryResponse;
import com.jimwhere.api.access.service.AccessHistoryService;
import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.user.dto.response.QrIssueResponse;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccessHistoryController {

  private final AccessHistoryService accessHistoryService;


  @PostMapping("/user/access/history")
  public ResponseEntity<ApiResponse<QrIssueResponse>> createAccessHistory(
      @RequestBody CreateAccessHistoryRequest request,
      @AuthenticationPrincipal CustomUser user
  ) {
    String userName = user.getUsername();
    QrIssueResponse response = accessHistoryService.createAccessHistory(request, userName);

    return ResponseEntity.ok(ApiResponse.success(response));
  }


  @PutMapping("/user/access/history/{accessHistoryCode}")
  public ResponseEntity<QrIssueResponse> updateAccessHistory(
      @AuthenticationPrincipal CustomUser user,
      @RequestBody CreateAccessHistoryRequest request,
      @PathVariable Long accessHistoryCode
  ) {
    String userName = user.getUsername();
    QrIssueResponse response = accessHistoryService.updateAccessHistory(request, userName,accessHistoryCode);
    return ResponseEntity.ok(response);
  }
  @GetMapping("/user/access/history")
  public PageResponse<AccessHistoryResponse> getAccessList(@PageableDefault Pageable pageable,
      @AuthenticationPrincipal CustomUser user) {
      String userName = user.getUsername();
    Page<AccessHistoryResponse> accessHistoryList=accessHistoryService.getAccessHistoryList(pageable,userName);
    return PageResponse.of(accessHistoryList);
  }
  @GetMapping("/admin/access/history")
  public PageResponse<AccessHistoryResponse> getAccessListAll(@PageableDefault Pageable pageable,
      @AuthenticationPrincipal CustomUser user) {
    Page<AccessHistoryResponse> accessHistoryList=accessHistoryService.getAccessHistoryListAll(pageable);
    return PageResponse.of(accessHistoryList);
  }
  @GetMapping("/user/access/history/{accessHistoryCode}")
  public ResponseEntity<ApiResponse<AccessHistoryDetailResponse>> selectAccessHistoryByCode(
      @PathVariable Long accessHistoryCode
  ) {
    AccessHistoryDetailResponse detail = accessHistoryService.selectDetailByCode(accessHistoryCode);

    return ResponseEntity.ok(ApiResponse.success(detail));
  }
}


