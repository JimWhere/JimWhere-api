package com.jimwhere.api.notice.dto.response;



import java.time.LocalDateTime;

public record NoticeResponse (
    Long noticeCode,
    String noticeTitle,
    String noticeContent,
    LocalDateTime createdAt,
    LocalDateTime updatedAt){

}
