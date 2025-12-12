package com.jimwhere.api.reservation.dto.response;

import com.jimwhere.api.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminReservationResponse {

    private Long reservationCode;
    private String ownerName;
    private String businessNumber;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String roomName;
    private String paymentStatus;
    private Long reservationAmount;
    private Long paymentAmount;

    public static AdminReservationResponse from(Reservation reservation) {


        String paymentStatus = (reservation.getOrderId() == null)
                ? "PENDING"
                : "PAID";

        return new AdminReservationResponse(
                reservation.getReservationCode(),
                reservation.getUser().getPName(),
                reservation.getUser().getUserBusinessNumber(),
                reservation.getStartAt(),
                reservation.getEndAt(),
                reservation.getRoom().getRoomName(),
                paymentStatus,
                reservation.getReservationAmount(),
                reservation.getReservationAmount()
        );
    }
}