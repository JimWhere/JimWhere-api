package com.jimwhere.api.reservation.dto;

import java.time.LocalDateTime;

public record ReservationResponse(
    Long reservationCode,
    Long userCode,
    String reservationStatus,
    LocalDateTime startAt,
    LocalDateTime endAt,
    Long reservationAmount,
    Long roomCode,
    Long roomTypeCode
) {

}
