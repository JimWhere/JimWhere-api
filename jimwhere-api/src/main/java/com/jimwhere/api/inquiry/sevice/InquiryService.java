package com.jimwhere.api.inquiry.sevice;

import com.jimwhere.api.inquiry.dto.request.CreateInquiryRequest;
import com.jimwhere.api.inquiry.dto.response.InquiryListResponse;
import com.jimwhere.api.inquiry.dto.response.InquiryResponse;
import com.jimwhere.api.inquiry.dto.request.UpdateAnswerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryService {

  String createInquiry(CreateInquiryRequest request,String userName);
  String updateAnswer(Long inquiryCode, UpdateAnswerRequest request,String userName);
  String deleteInquiry(Long inquiryCode);
  InquiryResponse getInquiry(Long inquiryCode);
  Page<InquiryListResponse> getInquiryList(Pageable pageable);
}
