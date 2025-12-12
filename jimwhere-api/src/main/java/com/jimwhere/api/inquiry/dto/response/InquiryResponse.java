package com.jimwhere.api.inquiry.dto.response;

import com.jimwhere.api.inquiry.domain.Inquiry;
import java.time.LocalDateTime;


public record InquiryResponse(
  Long inquiryCode,
  String inquiryTitle,
  String inquiryContent,
  LocalDateTime createdAt,
  String inquiryAnswer,
  String userName,
  String answerName,
  LocalDateTime answeredAt
)
{
  public static InquiryResponse form(Inquiry inquiry){

    String answer = null;
    if (inquiry.getInquiryAnswer() != null) {
      answer = inquiry.getInquiryAnswer(); // Lazy 초기화 필요
    }
    String answerName = null;
    if (inquiry.getAdmin() != null) {
      answerName = inquiry.getAdmin().getPName(); // Lazy 초기화 필요
    }
    LocalDateTime answeredAt = null;
    if (inquiry.getAnsweredAt() != null) {
      answeredAt = inquiry.getAnsweredAt(); // Lazy 초기화 필요
    }
  return new InquiryResponse(
      inquiry.getInquiryCode(),
      inquiry.getInquiryTitle(),
      inquiry.getInquiryContent(),
      inquiry.getCreatedAt(),
      answer,
      inquiry.getUser().getPName(),
      answerName,
      answeredAt
  );
}

}
