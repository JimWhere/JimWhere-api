package com.jimwhere.api.notice.service;

import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.notice.domain.Notice;
import com.jimwhere.api.notice.dto.request.CreateNoticeRequest;
import com.jimwhere.api.notice.dto.response.NoticeListResponse;
import com.jimwhere.api.notice.dto.response.NoticeResponse;
import com.jimwhere.api.notice.dto.request.UpdateNoticeRequest;
import com.jimwhere.api.notice.repository.NoticeRepository;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
  private final NoticeRepository noticeRepository;
  private final UserRepository userRepository;
  @Override
  public String createNotice(CreateNoticeRequest request,String userName) {
    if(request.noticeContent()==null|| request.noticeContent().isEmpty()){
      throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT);
    }
    if(request.noticeTitle()==null|| request.noticeTitle().isEmpty()){
      throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT);
    }
    User user=userRepository.findByUserId(userName)
        .orElseThrow(()->new CustomException(ErrorCode.INVALID_USER_ID));
    Notice notice = Notice.createNotice(
        request.noticeTitle(),
        request.noticeContent(),
        user
    );
    noticeRepository.save(notice);
    return "생성완료";
  }

  @Override
  @Transactional
  public String deleteNotice(Long noticeCode) {
    Notice notice=noticeRepository.findById(noticeCode).orElseThrow(()-> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
    if(notice.getIsDeleted()){
      throw new CustomException(ErrorCode.NOTICE_NOT_FOUND);
    }//db 에서 isDeleted가 0으로
    notice.deleteNotice();
    return "공지사항이 삭제되었습니다.";
  }

  @Override
  public NoticeResponse findNotice(Long noticeCode) {

    Notice notice = noticeRepository.findByNoticeCodeAndIsDeletedFalse(noticeCode)
        .orElseThrow(()-> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
    NoticeResponse noticeResponse=new NoticeResponse(
        notice.getNoticeCode(),
        notice.getNoticeTitle(),
        notice.getNoticeContent(),
        notice.getCreatedAt(),
        notice.getUpdatedAt()
    );
    return noticeResponse;
  }

  @Override
  @Transactional
  public String updateNotice(Long noticeCode, UpdateNoticeRequest request) {

    Notice notice=noticeRepository.findById(noticeCode).orElseThrow( ()-> new CustomException(ErrorCode.NOTICE_NOT_FOUND));


    if(request.noticeTitle()!=null&&!request.noticeTitle().isEmpty()){
      notice.updateTitle(request.noticeTitle());
    }else if(request.noticeContent()==null||request.noticeContent().isEmpty()){
      throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT);
    }
    if(request.noticeContent()!=null&&!request.noticeContent().isEmpty()){
      notice.updateContent(request.noticeContent());
    }
    noticeRepository.save(notice);
    return "공지사항이 수정되었습니다.";
  }

  @Override
  public Page<NoticeListResponse> findNoticeListAll(Pageable pageable) {

    Page<Notice> page = noticeRepository.findByIsDeletedFalse(pageable);
    return page.map(NoticeListResponse::from);
  }


}