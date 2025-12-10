package com.jimwhere.api.box.repository;

import com.jimwhere.api.box.domain.Box;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
    List<Box> findByRoomRoomCode(Long roomCode);
    long countByRoomRoomCodeAndBoxPossibleStatus(Long roomCode, String boxPossibleStatus);

    @Query("SELECT COALESCE(SUM(b.boxCurrentCount), 0) FROM Box b WHERE b.room.roomCode = :roomCode")
    Long sumBoxCurrentCountByRoomCode(@Param("roomCode") Long roomCode);
}
