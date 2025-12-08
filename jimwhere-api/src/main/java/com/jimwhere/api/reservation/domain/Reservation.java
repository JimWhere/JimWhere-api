package com.jimwhere.api.reservation.domain;

import com.jimwhere.api.global.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_code")
    private Long reservationCode;

    @Column(name = "user_code", nullable = false)
    private Long userCode;

    @Builder.Default
    @Column(name = "reservation_status", length = 10, nullable = false)
    private String reservationStatus = "PENDING";

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "reservation_amount", nullable = false)
    private Long reservationAmount;

    @Column(name = "room_code", nullable = false)
    private Long roomCode;

    @Column(name = "order_id", length = 64, nullable = false)
    private String orderId;

}
