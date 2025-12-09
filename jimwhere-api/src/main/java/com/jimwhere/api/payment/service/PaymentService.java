package com.jimwhere.api.payment.service;

import com.jimwhere.api.payment.client.TossPaymentClient;
import com.jimwhere.api.payment.dto.request.TossConfirmRequest;
import com.jimwhere.api.payment.dto.request.TossInitRequest;
import com.jimwhere.api.payment.dto.response.TossConfirmResponse;
import com.jimwhere.api.payment.dto.response.TossInitResponse;
import com.jimwhere.api.payment.properties.TossPaymentProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TossPaymentProperties properties;
    private final TossPaymentClient tossPaymentClient;


    //  결제 준비

    @Transactional(readOnly = true)
    public TossInitResponse initPayment(TossInitRequest request) {
        // 임시 주문ID (나중에 reservationCode 연동할 때 규칙 바꾸면 됨)
        String orderId = "JW-" + System.currentTimeMillis();

        return TossInitResponse.builder()
                .orderId(orderId)
                .amount(request.getAmount())
                .orderName(request.getOrderName())
                .clientKey(properties.getClientKey())
                .successUrl(properties.getSuccessUrl())
                .failUrl(properties.getFailUrl())
                .build();
    }


     // 결제 확정 (나중에 사용할 용도)

    @Transactional
    public TossConfirmResponse confirmPayment(TossConfirmRequest request) {
        return tossPaymentClient.confirmPayment(request);
    }
}
