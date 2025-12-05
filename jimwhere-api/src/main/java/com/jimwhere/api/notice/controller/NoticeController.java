package com.jimwhere.api.notice.controller;

import com.jimwhere.api.notice.dto.CreateNoticeRequest;
import com.jimwhere.api.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeController {
  NoticeService noticeService;

  @PreAuthorize("hasAuthority('')")
  @PostMapping("/")
  public ResponseEntity<String> createNotice(@RequestBody CreateNoticeRequest request) {
    return ResponseEntity.ok().body(noticeService.createNotice(request));
  }



}
