package com.jimwhere.api.notice.controller;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.notice.dto.request.CreateNoticeRequest;
import com.jimwhere.api.notice.dto.response.NoticeListResponse;
import com.jimwhere.api.notice.dto.response.NoticeResponse;
import com.jimwhere.api.notice.dto.request.UpdateNoticeRequest;
import com.jimwhere.api.notice.service.NoticeService;
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
@Tag(name="공지사항 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NoticeController {
  private final NoticeService noticeService;


  //관리자 공지사항 생성
  @Operation(
      summary = "공지사항 생성", description = "관리자가 공지사항을 작성하는 api"
  )
  @PostMapping("/admin/notice")
  public ResponseEntity<ApiResponse<String>> createNotice(@RequestBody CreateNoticeRequest request,
      @AuthenticationPrincipal CustomUser user) {
    String userName=user.getUsername();
    return ResponseEntity.ok(ApiResponse.success(noticeService.createNotice(request,userName)));
  }
  // 관리자 공지사항 삭제
  @Operation(
      summary = "공지사항 삭제", description = "관리자가 공지사항을 삭제하는 api"
  )
  @DeleteMapping("/admin/notice/{noticeCode}")
  public ResponseEntity<ApiResponse<String>> deleteNotice(@PathVariable Long noticeCode) {
    return ResponseEntity.ok(ApiResponse.success(noticeService.deleteNotice(noticeCode)));
  }

  //공지사항 수정
  @Operation(
      summary = "공지사항 수정", description = "관리자가 공지사항을 수정하는 api"
  )
  @PutMapping("/admin/notice/{noticeCode}")
  public ResponseEntity<ApiResponse<String>> updateNotice(@PathVariable Long noticeCode ,
      @RequestBody UpdateNoticeRequest request) {
    return ResponseEntity.ok(ApiResponse.success(noticeService.updateNotice(noticeCode,request)));
  }

  @Operation(
      summary = "공지사항 상세 조회", description = "관리자와 유저가 공지사항을 상세조회하는 api"
  )
  @GetMapping("/notice/{noticeCode}")//상세조회
  public ResponseEntity<ApiResponse<NoticeResponse>> findNotice(@PathVariable Long noticeCode) {
    return ResponseEntity.ok(ApiResponse.success(noticeService.findNotice(noticeCode)));
  }

  //공지사항 리스트 조회
  @Operation(
      summary = "공지사항 리스트 조회", description = "관리자와 유저가 공지사항 전체 리스트를 조회하는 api"
  )
  @GetMapping("/notice") //리스트 조회
  public ApiResponse<PageResponse<NoticeListResponse>> getNoticeList(@PageableDefault Pageable pageable) {
    Page<NoticeListResponse> noticeList=noticeService.findNoticeListAll(pageable);
    return ApiResponse.success(PageResponse.of(noticeList));
  }


}
