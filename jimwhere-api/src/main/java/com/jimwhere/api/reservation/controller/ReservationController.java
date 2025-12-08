package com.jimwhere.api.reservation.controller;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.reservation.dto.request.ReservationCreateRequest;
import com.jimwhere.api.reservation.dto.response.AdminReservationResponse;
import com.jimwhere.api.reservation.dto.response.ReservationResponse;
import com.jimwhere.api.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // USER

    @PostMapping("/user/reservations")
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationCreateRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        String username = user.getUsername();
        return ResponseEntity.ok(reservationService.createReservation(username, request));
    }

    @GetMapping("/user/reservations")
    public PageResponse<ReservationResponse> getMyReservations(
            @AuthenticationPrincipal CustomUser user,
            @PageableDefault Pageable pageable
    ) {
        String username = user.getUsername();
        Page<ReservationResponse> page = reservationService.getMyReservations(username, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/user/reservations/{reservationCode}")
    public ResponseEntity<ReservationResponse> getMyReservationDetail(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable Long reservationCode
    ) {
        String username = user.getUsername();
        return ResponseEntity.ok(reservationService.getMyReservationDetail(username, reservationCode));
    }

    // ADMIN

    @GetMapping("/admin/reservations")
    public PageResponse<AdminReservationResponse> getReservationsForAdmin(
            @PageableDefault Pageable pageable
    ) {
        Page<AdminReservationResponse> page = reservationService.getReservationsForAdmin(pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/admin/reservations/{reservationCode}")
    public ResponseEntity<AdminReservationResponse> getReservationDetailForAdmin(
            @PathVariable Long reservationCode
    ) {
        return ResponseEntity.ok(reservationService.getReservationDetailForAdmin(reservationCode));
    }
}
