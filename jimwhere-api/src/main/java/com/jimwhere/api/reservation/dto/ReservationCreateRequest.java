package com.jimwhere.api.reservation.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record ReservationCreateRequest(
    @NotNull Long roomCode,
    @NotNull Long roomTypeCode,
    @NotNull LocalDateTime startAt,
    @NotNull LocalDateTime endAt,
    Long reservationAmount
) {

}
