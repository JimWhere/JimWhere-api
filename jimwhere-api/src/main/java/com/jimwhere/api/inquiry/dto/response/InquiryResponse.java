package com.jimwhere.api.inquiry.dto.response;

import java.time.LocalDateTime;

public record InquiryResponse(
  Long inquiryCode,
  String inquiryTitle,
  String inquiryContent,
  LocalDateTime createdAt,
  String inquiryAnswer,
  LocalDateTime answeredAt

)
{

}
