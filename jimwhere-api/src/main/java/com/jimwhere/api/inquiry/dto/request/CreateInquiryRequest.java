package com.jimwhere.api.inquiry.dto.request;

public record CreateInquiryRequest(
    String inquiryTitle,
    String inquiryContent
) {

}
