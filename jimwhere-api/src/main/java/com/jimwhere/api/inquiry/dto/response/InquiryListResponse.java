package com.jimwhere.api.inquiry.dto.response;

import com.jimwhere.api.inquiry.domain.Inquiry;
import java.time.LocalDateTime;

public record InquiryListResponse(
    Long inquiryCode,
    String inquiryTitle,
    LocalDateTime createdAt,
    String userName,
    String answer
) {
  public static InquiryListResponse from(Inquiry inquiry) {
    return new InquiryListResponse(
        inquiry.getInquiryCode(),
        inquiry.getInquiryTitle(),
        inquiry.getCreatedAt(),
        inquiry.getUser().getUserId(),
        inquiry.getInquiryAnswer()
    );
  }
}