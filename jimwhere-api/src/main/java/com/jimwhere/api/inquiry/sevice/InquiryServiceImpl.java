package com.jimwhere.api.inquiry.sevice;

import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
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
    if(request.inquiryContent()==null|| request.inquiryContent().isEmpty()){
      throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT);
    }
    if(request.inquiryTitle()==null|| request.inquiryTitle().isEmpty()){
      throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT);
    }

    User user=userRepository.findByUserId(userName)
        .orElseThrow(()->new CustomException(ErrorCode.INVALID_USER_ID));
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
    if(request.answer()==null|| request.answer().isEmpty()){
      throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT);
    }
    Inquiry inquiry=inquiryRepository.findByInquiryCodeAndIsDeletedFalse(inquiryCode).orElseThrow(
        ()->new CustomException(ErrorCode.INQUIRY_NOT_FOUND)
    );
    User user=userRepository.findByUserId(userName)
        .orElseThrow(()->new CustomException(ErrorCode.INVALID_USER_ID));
    inquiry.answerInquiry(request.answer(),user);
    inquiryRepository.save(inquiry);
    return "답변이 성공적으로 달렸습니다.";
  }

  @Override
  public String deleteInquiry(Long inquiryCode) {
    Inquiry inquiry=inquiryRepository.findById(inquiryCode).orElseThrow(
        ()->new CustomException(ErrorCode.INQUIRY_NOT_FOUND)
    );
    if(inquiry.getIsDeleted()){
      throw new CustomException(ErrorCode.INQUIRY_NOT_FOUND);
    }
    inquiry.deleteInquiry();
    inquiryRepository.save(inquiry);
    return "삭제 되었습니다.";
  }

  @Override
  public InquiryResponse getInquiry(Long inquiryCode) {
    Inquiry inquiry= inquiryRepository.findByInquiryCodeAndIsDeletedFalse(inquiryCode)
        .orElseThrow(        ()->new CustomException(ErrorCode.INQUIRY_NOT_FOUND));

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
  public Page<InquiryListResponse> getInquiryList(Pageable pageable, String userName) {
    User user=userRepository.findByUserId(userName)
        .orElseThrow(()->new CustomException(ErrorCode.INVALID_USER_ID));
    Page<Inquiry> page=inquiryRepository.findByUserAndIsDeletedFalse(user, pageable);
    return page.map(InquiryListResponse::from);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<InquiryListResponse> getInquiryListAll(Pageable pageable) {
    Page<Inquiry> page = inquiryRepository.findByIsDeletedFalse(pageable);
    return page.map(InquiryListResponse::from);
  }
}
