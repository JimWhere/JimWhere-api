package com.jimwhere.api.notice.service;

import com.jimwhere.api.notice.domain.Notice;
import com.jimwhere.api.notice.dto.CreateNoticeRequest;
import com.jimwhere.api.notice.repository.NoticeRepository;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
  NoticeRepository noticeRepository;
  UserRepository userRepository;
  public String createNotice(CreateNoticeRequest request) {
    if(request==null){
      throw new IllegalArgumentException("request is null");
    }
    Notice notice = Notice.builder()
        .noticeTitle(request.noticeContent())
        .noticeContent(request.noticeContent())
        .user(userRepository.findById(request.userCode()).orElse(null))
        .build();
    noticeRepository.save(notice);
    return "생성완료";
  }



}
