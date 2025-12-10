package com.jimwhere.api.notice.controller;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.notice.dto.request.CreateNoticeRequest;
import com.jimwhere.api.notice.dto.response.NoticeListResponse;
import com.jimwhere.api.notice.dto.response.NoticeResponse;
import com.jimwhere.api.notice.dto.request.UpdateNoticeRequest;
import com.jimwhere.api.notice.service.NoticeService;
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
public class NoticeController {
  private final NoticeService noticeService;


  @PostMapping("/admin/notice")
  public ResponseEntity<ApiResponse<String>> createNotice(@RequestBody CreateNoticeRequest request,
      @AuthenticationPrincipal CustomUser user) {
    String userName=user.getUsername();
    return ResponseEntity.ok(ApiResponse.success(noticeService.createNotice(request,userName)));
  }
  @DeleteMapping("/admin/notice/{noticeCode}")
  public ResponseEntity<ApiResponse<String>> deleteNotice(@PathVariable Long noticeCode) {
    return ResponseEntity.ok(ApiResponse.success(noticeService.deleteNotice(noticeCode)));
  }
  @PutMapping("/admin/notice/{noticeCode}")
  public ResponseEntity<ApiResponse<String>> updateNotice(@PathVariable Long noticeCode ,
      @RequestBody UpdateNoticeRequest request) {
    return ResponseEntity.ok(ApiResponse.success(noticeService.updateNotice(noticeCode,request)));
  }
  @GetMapping("/notice/{noticeCode}")//상세조회
  public ResponseEntity<ApiResponse<NoticeResponse>> findNotice(@PathVariable Long noticeCode) {
    return ResponseEntity.ok(ApiResponse.success(noticeService.getNotice(noticeCode)));
  }
  @GetMapping("/notice") //리스트 조회
  public ApiResponse<PageResponse<NoticeListResponse>> getNoticeList(@PageableDefault Pageable pageable) {
    Page<NoticeListResponse> noticeList=noticeService.getNoticeList(pageable);
    return ApiResponse.success(PageResponse.of(noticeList));
  }


}
