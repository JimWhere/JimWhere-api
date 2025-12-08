package com.jimwhere.api.paymentHistory.repository;

import com.jimwhere.api.paymentHistory.domain.PaymentHistory;
import com.jimwhere.api.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    // 마이페이지 - 내 결제 내역 목록
    Page<PaymentHistory> findByReservationUser(User user, Pageable pageable);

    // 내 결제 상세
    Optional<PaymentHistory> findByPaymentHistoryCodeAndReservationUser(Long paymentHistoryCode, User user);
}
