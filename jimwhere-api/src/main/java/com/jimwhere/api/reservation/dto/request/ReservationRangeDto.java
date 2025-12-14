package com.jimwhere.api.reservation.dto.request;
import java.time.LocalDateTime;

public record ReservationRangeDto(LocalDateTime startAt, LocalDateTime endAt) {

}
