package com.jimwhere.api.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class DashboardReservationDto {
    private Long reservationCode;
    private String roomName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Long days;
}
