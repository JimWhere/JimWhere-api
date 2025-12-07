package com.jimwhere.api.notice.dto.request;

public record UpdateNoticeRequest (
    String noticeTitle,
  String noticeContent
){
}
