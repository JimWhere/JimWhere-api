package com.jimwhere.api.inout.controller;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;

import com.jimwhere.api.inout.dto.request.UpdateInOutHistoryRequest;
import com.jimwhere.api.inout.dto.response.InOutHistoryResponse;
import com.jimwhere.api.inout.service.InOutHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InOutHistoryController {
  private final InOutHistoryService inOutHistoryService;

  @PutMapping("/user/in/out/history/{inOutHistoryCode}")
  public ResponseEntity<String> updateInOutHistory(
      @PathVariable Long inOutHistoryCode,
      @RequestBody UpdateInOutHistoryRequest request,
      @AuthenticationPrincipal CustomUser user
  ){
    String userName=user.getUsername();
    return ResponseEntity.ok().body(inOutHistoryService.updateInOutHistory(inOutHistoryCode,request));
  }
  @GetMapping("/user/in/out/history")
  public PageResponse<InOutHistoryResponse> findInOutHistoryList(
      @PageableDefault Pageable pageable,
      @AuthenticationPrincipal CustomUser user
  ){
    String userName=user.getUsername();
    Page<InOutHistoryResponse> inoutHistoryList=inOutHistoryService.findInOutHistoryList(pageable,userName);
    return PageResponse.of(inoutHistoryList);
  }
  @GetMapping("/admin/in/out/history")
  public PageResponse<InOutHistoryResponse> findInOutHistoryListAll(
      @PageableDefault Pageable pageable
  ){
    Page<InOutHistoryResponse> inoutHistoryList=inOutHistoryService.findInOutHistoryListAll(pageable);
    return PageResponse.of(inoutHistoryList);
  }
}
