package com.jimwhere.api.reservation.repository;

import com.jimwhere.api.reservation.domain.Reservation;
import com.jimwhere.api.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 토스 결제 승인(confirm)에서 orderId로 예약 찾을 때 사용
    Optional<Reservation> findByOrderId(String orderId);

    // 마이페이지 - 내 예약 목록 조회용
    Page<Reservation> findByUser(User user, Pageable pageable);

    // 내 예약 상세 조회 (본인 것만 조회)
    Optional<Reservation> findByReservationCodeAndUser(Long reservationCode, User user);

    Page<Reservation> findByUserUserCode(Long userCode, Pageable pageable);
}
