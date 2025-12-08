package com.jimwhere.api.reservation.dto.response;

import com.jimwhere.api.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationResponse {

    private Long reservationCode;
    private Long roomCode;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Long reservationAmount;
    private String orderId;
    private LocalDateTime createdAt;

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getReservationCode(),
                reservation.getRoom().getRoomCode(),
                reservation.getStartAt(),
                reservation.getEndAt(),
                reservation.getReservationAmount(),
                reservation.getOrderId(),
                reservation.getCreatedAt()
        );
    }
}
