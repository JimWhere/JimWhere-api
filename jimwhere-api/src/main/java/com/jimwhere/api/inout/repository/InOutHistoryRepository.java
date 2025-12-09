package com.jimwhere.api.inout.repository;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.inout.domain.InOutHistory;
import com.jimwhere.api.inout.dto.response.InOutDetailResponse;
import com.jimwhere.api.user.domain.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InOutHistoryRepository extends JpaRepository<InOutHistory, Long> {

  @Query("""
        select new com.jimwhere.api.inout.dto.response.InOutDetailResponse(
            i.inOutHistoryCode,
            i.inOutType,
            i.inOutName,
            i.inOutQuantity,
            i.box.boxName
        )
        from InOutHistory i
        where i.accessHistory = :accessHistory
    """)
  List<InOutDetailResponse> findDetailsByAccessHistory(@Param("accessHistory") AccessHistory history);
  Page<InOutHistory> findByAccessHistory_User(User user, Pageable pageable);
  List<InOutHistory> findByAccessHistory_AccessHistoryCode(Long accessHistoryCode);
}
