package com.jimwhere.api.notice.repository;

import com.jimwhere.api.notice.domain.Notice;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  Page<Notice> findByIsDeletedFalse(Pageable pageable);


  Optional<Notice> findByNoticeCodeAndIsDeletedFalse(Long noticeCode);


}
