package com.jimwhere.api.inout.repository;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.inout.domain.InOutHistory;
import com.jimwhere.api.inout.domain.InOutType;
import com.jimwhere.api.inout.dto.response.InOutDetailResponse;
import com.jimwhere.api.inout.dto.response.InOutHistoryAllResponse;
import com.jimwhere.api.inout.dto.response.InOutHistoryResponse;
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

  @Query("""
  SELECT new com.jimwhere.api.inout.dto.response.InOutHistoryResponse(
      ih.inOutHistoryCode,
      ih.inOutType,
      ih.inOutName,
      ih.inOutQuantity,
      ah.roomCode,
      b.boxName
  )
  FROM InOutHistory ih
  JOIN ih.accessHistory ah
  JOIN ah.user u
  JOIN ih.box b
  WHERE
    (:roomCode IS NULL OR ah.roomCode = :roomCode)
    AND (:boxName IS NULL OR b.boxName LIKE %:boxName%)
    AND u.userCode = :userCode
    AND ah.accessResult='Y'
    AND (:inOutType IS NULL OR ih.inOutType = :inOutType)
    AND (:inOutName IS NULL OR ih.inOutName LIKE %:inOutName%)
    AND (:inOutHistoryCode IS NULL OR ih.inOutHistoryCode = :inOutHistoryCode)
    
""")
  Page<InOutHistoryResponse> findUser(
      @Param("roomCode") Long roomCode,
      @Param("boxName") String boxName,
      @Param("userCode") Long userCode,
      @Param("inOutType") InOutType inOutType,
      @Param("inOutName") String inOutName,
      @Param("inOutHistoryCode") Long inOutHistoryCode,
      Pageable pageable
  );

  List<InOutHistory> findByAccessHistory_AccessHistoryCode(Long accessHistoryCode);

  @Query("""
  SELECT new com.jimwhere.api.inout.dto.response.InOutHistoryAllResponse(
      ih.inOutHistoryCode,
      ih.inOutType,
      ih.inOutName,
      ih.inOutQuantity,
      ah.roomCode,
      u.userId,
      b.boxName
  )
  FROM InOutHistory ih
  JOIN ih.accessHistory ah
  JOIN ah.user u
    JOIN ih.box b
  WHERE
    (:roomCode IS NULL OR ah.roomCode = :roomCode)
    AND ah.accessResult='Y'
    AND (:boxName IS NULL OR b.boxName LIKE %:boxName%)
    AND (:userId IS NULL OR u.userId LIKE %:userId%)
""")
  Page<InOutHistoryAllResponse> findAllWithUser(
      @Param("roomCode") Long roomCode,
      @Param("boxName") String boxName,
      @Param("userId") String userId,
      Pageable pageable
  );

}
