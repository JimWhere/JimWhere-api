package com.jimwhere.api.inout.repository;

import com.jimwhere.api.inout.domain.InOutHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InOutHistoryRepository extends JpaRepository<InOutHistory, Integer> {

}
