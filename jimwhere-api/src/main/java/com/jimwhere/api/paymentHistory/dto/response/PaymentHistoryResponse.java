package com.jimwhere.api.paymentHistory.dto.response;

import com.jimwhere.api.paymentHistory.domain.PaymentHistory;
import com.jimwhere.api.paymentHistory.domain.PaymentStatus;
import com.jimwhere.api.paymentHistory.domain.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PaymentHistoryResponse {

    private Long paymentHistoryCode;
    private Long userCode;
    private Long reservationCode;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private Long paymentAmount;
    private String paymentMethod;
    private String paymentKey;
    private LocalDateTime createdAt;

    public static PaymentHistoryResponse from(PaymentHistory paymentHistory) {
        return new PaymentHistoryResponse(
                paymentHistory.getPaymentHistoryCode(),
                paymentHistory.getReservation().getUser().getUserCode(),
                paymentHistory.getReservation().getReservationCode(),
                paymentHistory.getPaymentType(),
                paymentHistory.getPaymentStatus(),
                paymentHistory.getPaymentAmount(),
                paymentHistory.getPaymentMethod(),
                paymentHistory.getPaymentKey(),
                paymentHistory.getCreatedAt()
        );
    }
}
