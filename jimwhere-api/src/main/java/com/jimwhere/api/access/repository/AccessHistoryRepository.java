package com.jimwhere.api.access.repository;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.user.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessHistoryRepository extends JpaRepository<AccessHistory,Long> {



  Page<AccessHistory> findByUser(User user, Pageable pageable);

}
