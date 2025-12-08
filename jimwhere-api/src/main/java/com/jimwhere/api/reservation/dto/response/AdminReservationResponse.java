package com.jimwhere.api.reservation.dto.response;

import com.jimwhere.api.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminReservationResponse {

    private Long reservationCode;
    private Long userCode;
    private Long roomCode;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Long reservationAmount;
    private String orderId;
    private LocalDateTime createdAt;

    public static AdminReservationResponse from(Reservation reservation) {
        return new AdminReservationResponse(
                reservation.getReservationCode(),
                reservation.getUser().getUserCode(),
                reservation.getRoom().getRoomCode(),
                reservation.getStartAt(),
                reservation.getEndAt(),
                reservation.getReservationAmount(),
                reservation.getOrderId(),
                reservation.getCreatedAt()
        );
    }
}
