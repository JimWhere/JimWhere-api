package com.jimwhere.api.notice.dto.request;


public record CreateNoticeRequest(
    String noticeTitle,
    String noticeContent
) {

}
