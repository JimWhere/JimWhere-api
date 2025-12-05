package com.jimwhere.api.notice.service;


import com.jimwhere.api.notice.dto.CreateNoticeRequest;

public interface NoticeService {
  String createNotice(CreateNoticeRequest request);
}
