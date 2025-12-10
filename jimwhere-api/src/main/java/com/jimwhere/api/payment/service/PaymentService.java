package com.jimwhere.api.payment.service;

import com.jimwhere.api.payment.client.TossPaymentClient;
import com.jimwhere.api.payment.dto.request.TossConfirmRequest;
import com.jimwhere.api.payment.dto.request.TossInitRequest;
import com.jimwhere.api.payment.dto.response.TossConfirmResponse;
import com.jimwhere.api.payment.dto.response.TossInitResponse;
import com.jimwhere.api.payment.properties.TossPaymentProperties;
import com.jimwhere.api.paymentHistory.domain.PaymentHistory;
import com.jimwhere.api.paymentHistory.repository.PaymentHistoryRepository;
import com.jimwhere.api.reservation.domain.Reservation;
import com.jimwhere.api.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TossPaymentProperties properties;
    private final TossPaymentClient tossPaymentClient;
    private final ReservationRepository reservationRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;


    //  결제 준비

    @Transactional
    public TossInitResponse initPayment(TossInitRequest request) {


        // 1) 예약 조회
        Reservation reservation = reservationRepository.findById(request.getReservationCode())
                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));

        // 2) orderId = 예약코드(Long) 문자열
        String orderId = String.format("JW_%06d", reservation.getReservationCode());
        reservation.updateOrderId(orderId);

        // 3) 주문명 = 창고/룸 이름
        String orderName = reservation.getRoom().getRoomName();


        // 4) 금액
        Long amount = reservation.getReservationAmount();

        return TossInitResponse.builder()
                .orderId(orderId)
                .amount(amount)
                .orderName(orderName)
                .clientKey(properties.getClientKey())
                .successUrl(properties.getSuccessUrl())
                .failUrl(properties.getFailUrl())
                .build();
    }


    // 결제 확정 및 결제이력 저장

    @Transactional
    public TossConfirmResponse confirmPayment(TossConfirmRequest request) {
        // Toss 서버에 결제 승인 요청
        TossConfirmResponse response = tossPaymentClient.confirmPayment(request);

        Reservation reservation = reservationRepository.findByOrderId(response.getOrderId())
                .orElseThrow(() ->
                        new IllegalArgumentException("예약 정보를 찾을 수 없습니다. orderId=" + response.getOrderId()));

        // 결제 이력 엔티티 생성
        PaymentHistory paymentHistory = PaymentHistory.createPaid(
                reservation,
                response.getTotalAmount(),
                response.getMethod(),
                response.getPaymentKey()
        );

        // 5) 결제 이력 저장
        paymentHistoryRepository.save(paymentHistory);

        // 6) 프론트로 Toss 응답 그대로 반환
        return response;
    }
}

