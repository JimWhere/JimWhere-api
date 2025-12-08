package com.jimwhere.api.access.repository;

import com.jimwhere.api.access.domain.AccessHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessHistoryRepository extends JpaRepository<AccessHistory,Long> {

}
