package com.jimwhere.api.reservation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationCreateRequest {

    private Long roomCode;
    private Long roomTypeCode;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Long reservationAmount;

}
