package com.jimwhere.api.notice.service;


import com.jimwhere.api.notice.dto.request.CreateNoticeRequest;
import com.jimwhere.api.notice.dto.response.NoticeListResponse;
import com.jimwhere.api.notice.dto.response.NoticeResponse;
import com.jimwhere.api.notice.dto.request.UpdateNoticeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
  String createNotice(CreateNoticeRequest request,String userName);
  String deleteNotice(Long noticeCode);
  NoticeResponse getNotice(Long noticeCode);
  String updateNotice(Long noticeCode, UpdateNoticeRequest request);
  Page<NoticeListResponse> getNoticeList(Pageable pageable);
}
