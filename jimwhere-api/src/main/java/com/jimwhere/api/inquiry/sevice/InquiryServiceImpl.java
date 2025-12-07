package com.jimwhere.api.inquiry.sevice;

import com.jimwhere.api.inquiry.domain.Inquiry;
import com.jimwhere.api.inquiry.dto.request.CreateInquiryRequest;
import com.jimwhere.api.inquiry.dto.response.InquiryListResponse;
import com.jimwhere.api.inquiry.dto.response.InquiryResponse;
import com.jimwhere.api.inquiry.dto.request.UpdateAnswerRequest;
import com.jimwhere.api.inquiry.repository.InquiryRepository;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {
  private final InquiryRepository inquiryRepository;
  private final UserRepository userRepository;
  @Override
  public String createInquiry(CreateInquiryRequest request,String userName) {
    if(request==null){
      throw new IllegalArgumentException("request is null");
    }
    User user=userRepository.findByUserId(userName)
        .orElseThrow(()->new IllegalArgumentException("유저이름과 일치하는 유저를 찾을 수 업습니다."));
    Inquiry inquiry = Inquiry.createInquiry(
        request.inquiryTitle(),
        request.inquiryContent(),
        user
    );
    inquiryRepository.save(inquiry);
    return "문의가 생성 되었습니다.";
  }

  @Override
  public String updateAnswer(Long inquiryCode, UpdateAnswerRequest request,String userName) {
    if(inquiryCode==null){
      throw new IllegalArgumentException("문의를 찾을 수 없습니다.");
    }
    Inquiry inquiry=inquiryRepository.findById(inquiryCode).orElseThrow();
    User user=userRepository.findByUserId(userName)
        .orElseThrow(()->new IllegalArgumentException("유저이름과 일치하는 유저를 찾을 수 업습니다."));
    inquiry.answerInquiry(request.answer(),user);
    inquiryRepository.save(inquiry);
    return "답변이 성공적으로 달렸습니다.";
  }

  @Override
  public String deleteInquiry(Long inquiryCode) {
    if(inquiryCode==null){
      throw new IllegalArgumentException("문의를 찾을 수 없습니다.");
    }
    Inquiry inquiry=inquiryRepository.findById(inquiryCode).orElseThrow();
    inquiry.deleteInquiry();
    inquiryRepository.save(inquiry);
    return "삭제 되었습니다.";
  }

  @Override
  public InquiryResponse getInquiry(Long inquiryCode) {
    if(inquiryCode==null){
      throw new IllegalArgumentException("문의 찾을 수 없습니다.");
    }
    Inquiry inquiry= inquiryRepository.findByInquiryCodeAndIsDeletedFalse(inquiryCode)
        .orElseThrow(() -> new IllegalArgumentException("삭제된 문의 입니다."));

    return new InquiryResponse(
        inquiry.getInquiryCode(),
        inquiry.getInquiryTitle(),
        inquiry.getInquiryContent(),
        inquiry.getCreatedAt(),
        inquiry.getInquiryAnswer(),
        inquiry.getAnsweredAt()
    );
  }

  @Override
  @Transactional(readOnly = true)
  public Page<InquiryListResponse> getInquiryList(Pageable pageable) {
    Page<Inquiry> page = inquiryRepository.findByIsDeletedFalse(pageable);
    return page.map(InquiryListResponse::from);
  }
}
