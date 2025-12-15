package com.jimwhere.api.reservation.repository;

import com.jimwhere.api.reservation.domain.Reservation;
import com.jimwhere.api.room.domain.Room;
import com.jimwhere.api.user.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 토스 결제 승인(confirm)에서 orderId로 예약 찾을 때 사용
    Optional<Reservation> findByOrderId(String orderId);

    // 마이페이지 - 내 예약 목록 조회용
    Page<Reservation> findByUser(User user, Pageable pageable);

    // 내 예약 상세 조회 (본인 것만 조회)
    Optional<Reservation> findByReservationCodeAndUser(Long reservationCode, User user);

    Page<Reservation> findByUserUserCode(Long userCode, Pageable pageable);

    /* 내일 입주 예정 예약 찾기 (MOVE_IN_DUE) */
    List<Reservation> findByStartAtBetween(LocalDateTime start, LocalDateTime end);

    /* 3일 뒤 만기 예약 찾기 (EXPIRATION_D3) */
    List<Reservation> findByEndAtBetween(LocalDateTime start, LocalDateTime end);

    // 특정날짜기간동안 예약 존재하는지 확인 하기
    boolean existsByRoomRoomCodeAndStartAtLessThanAndEndAtGreaterThan(
        Long roomCode,
        LocalDateTime startAt,
        LocalDateTime endAt
    );

    Optional<Reservation> findByUserAndRoomAndStartAtAndEndAt(User user, Room room, LocalDateTime startAt, LocalDateTime endAt);


    // 룸의 기존 예약 내역 확인 하기 
    @Query("SELECT r FROM Reservation r WHERE r.room.roomCode = :roomCode AND r.startAt <= :to AND r.endAt >= :from")
    List<Reservation> findReservationsOverlappingRange(
        @Param("roomCode") Long roomCode,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );
}
