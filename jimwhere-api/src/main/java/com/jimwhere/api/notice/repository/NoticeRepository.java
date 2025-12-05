package com.jimwhere.api.notice.repository;

import com.jimwhere.api.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
