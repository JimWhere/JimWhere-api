package com.jimwhere.api.notice.dto;


public record CreateNoticeRequest(
    String noticeTitle,
    String noticeContent,
    Long userCode
) {

}
