package com.jimwhere.api.paymentHistory.service;

import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.paymentHistory.domain.PaymentHistory;
import com.jimwhere.api.paymentHistory.dto.response.PaymentHistoryResponse;
import com.jimwhere.api.paymentHistory.repository.PaymentHistoryRepository;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final UserRepository userRepository;

    // USER - 내 결제 내역 목록
    public Page<PaymentHistoryResponse> getMyPaymentHistories(String username, Pageable pageable) {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_USER_ID.getMessage()));

        Page<PaymentHistory> page = paymentHistoryRepository.findByReservationUser(user, pageable);
        return page.map(PaymentHistoryResponse::from);
    }

    // USER - 내 결제 내역 상세
    public PaymentHistoryResponse getMyPaymentHistoryDetail(String username, Long paymentHistoryCode) {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_USER_ID.getMessage()));

        PaymentHistory paymentHistory = paymentHistoryRepository
                .findByPaymentHistoryCodeAndReservationUser(paymentHistoryCode, user)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_REQUEST.getMessage()));

        return PaymentHistoryResponse.from(paymentHistory);
    }

    // ADMIN - 전체 결제 내역 목록
    public Page<PaymentHistoryResponse> getPaymentHistoriesForAdmin(Pageable pageable) {
        Page<PaymentHistory> page = paymentHistoryRepository.findAll(pageable);
        return page.map(PaymentHistoryResponse::from);
    }

    // ADMIN - 특정 결제 내역 상세
    public PaymentHistoryResponse getPaymentHistoryDetailForAdmin(Long paymentHistoryCode) {
        PaymentHistory paymentHistory = paymentHistoryRepository.findById(paymentHistoryCode)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_REQUEST.getMessage()));

        return PaymentHistoryResponse.from(paymentHistory);
    }
}
