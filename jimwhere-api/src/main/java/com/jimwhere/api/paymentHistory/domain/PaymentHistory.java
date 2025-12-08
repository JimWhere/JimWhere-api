package com.jimwhere.api.paymentHistory.domain;

import com.jimwhere.api.global.model.BaseTimeEntity;
import com.jimwhere.api.reservation.domain.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "payment_history",
        indexes = {
                @Index(name = "idx_payment_reservation", columnList = "reservation_code"),
                @Index(name = "idx_payment_key", columnList = "payment_key")
        }
)
public class PaymentHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_history_code")
    private Long paymentHistoryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_code", nullable = false)
    private Reservation reservation;

    @Column(name = "payment_amount", nullable = false)
    private Long paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    // 결제 성공 기록 생성
    public static PaymentHistory createPaid(
            Reservation reservation,
            Long amount,
            String method,
            String paymentKey
    ) {
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.reservation = reservation;
        paymentHistory.paymentAmount = amount;
        paymentHistory.paymentType = PaymentType.PAYMENT;
        paymentHistory.paymentMethod = method;
        paymentHistory.paymentKey = paymentKey;
        paymentHistory.paymentStatus = PaymentStatus.Y;
        return paymentHistory;
    }

    // 환불 기록 생성
    public static PaymentHistory createRefunded(
            Reservation reservation,
            Long amount,
            String method,
            String paymentKey
    ) {
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.reservation = reservation;
        paymentHistory.paymentAmount = amount;
        paymentHistory.paymentType = PaymentType.REFUND;
        paymentHistory.paymentMethod = method;
        paymentHistory.paymentKey = paymentKey;
        paymentHistory.paymentStatus = PaymentStatus.Y;
        return paymentHistory;
    }
}
