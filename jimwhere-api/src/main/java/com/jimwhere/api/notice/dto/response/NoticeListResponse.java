package com.jimwhere.api.notice.dto.response;

import com.jimwhere.api.notice.domain.Notice;
import java.time.LocalDateTime;

public record NoticeListResponse(
    Long noticeCode,
    String noticeTitle,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
    //String writerName
) {
  public static NoticeListResponse from(Notice notice) {
    return new NoticeListResponse(
        notice.getNoticeCode(),
        notice.getNoticeTitle(),
      notice.getCreatedAt(),
        notice.getUpdatedAt()
      //  notice.getUser().getName() //
    );
  }
}

