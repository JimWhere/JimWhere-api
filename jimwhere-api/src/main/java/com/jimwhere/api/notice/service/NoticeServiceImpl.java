package com.jimwhere.api.notice.service;

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
    if(request==null){
      throw new IllegalArgumentException("request is null");
    }
    User user=userRepository.findByUserId(userName)
        .orElseThrow(()->new IllegalArgumentException("유저이름과 일치하는 유저를 찾을 수 업습니다."));
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
    if(noticeCode==null) {
      throw new IllegalArgumentException("공지사항을 찾을 수 없습니다.");
    }
    Notice notice=noticeRepository.findById(noticeCode).orElseThrow();
    if(notice.getIsDeleted()){
      throw new IllegalArgumentException("공지사항을 찾을 수 없습니다.");
    }//db 에서 isDeleted가 0으로
    notice.deleteNotice();
    return "공지사항이 삭제되었습니다.";
  }

  @Override
  public NoticeResponse getNotice(Long noticeCode) {
    if(noticeCode==null){
      throw new IllegalArgumentException("공지사항을 찾을 수 없습니다.");
    }

    Notice notice = noticeRepository.findByNoticeCodeAndIsDeletedFalse(noticeCode)
        .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다."));
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
    if(noticeCode==null|| noticeRepository.findById(noticeCode).isEmpty()){
      throw new IllegalArgumentException("공지사항을 찾을 수 없습니다.");
    }

    Notice notice=noticeRepository.findById(noticeCode).orElseThrow();
    if(request.noticeTitle()!=null){
      notice.updateTitle(request.noticeTitle());
    }
    if(request.noticeContent()!=null){
      notice.updateContent(request.noticeContent());
    }
    noticeRepository.save(notice);
    return "공지사항이 수정되었습니다.";
  }

  @Override
  public Page<NoticeListResponse> getNoticeList(Pageable pageable) {

    Page<Notice> page = noticeRepository.findByIsDeletedFalse(pageable);
    return page.map(NoticeListResponse::from);
  }


}
