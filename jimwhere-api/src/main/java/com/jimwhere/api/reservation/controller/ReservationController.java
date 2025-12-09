package com.jimwhere.api.reservation.controller;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.reservation.dto.request.ReservationCreateRequest;
import com.jimwhere.api.reservation.dto.response.AdminReservationResponse;
import com.jimwhere.api.reservation.dto.response.ReservationResponse;
import com.jimwhere.api.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // USER

    @PostMapping("/user/reservations")
    public ApiResponse<ReservationResponse> createReservation(
            @RequestBody ReservationCreateRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        String username = user.getUsername();
        ReservationResponse response = reservationService.createReservation(username, request);
        return ApiResponse.success(response);
    }

    @GetMapping("/user/reservations")
    public ApiResponse<PageResponse<ReservationResponse>> getMyReservations(
            @AuthenticationPrincipal CustomUser user,
            @PageableDefault Pageable pageable
    ) {
        String username = user.getUsername();
        Page<ReservationResponse> page = reservationService.getMyReservations(username, pageable);
        return ApiResponse.success(PageResponse.of(page));
    }

    @GetMapping("/user/reservations/{reservationCode}")
    public ApiResponse<ReservationResponse> getMyReservationDetail(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable Long reservationCode
    ) {
        String username = user.getUsername();
        ReservationResponse response = reservationService.getMyReservationDetail(username, reservationCode);
        return ApiResponse.success(response);
    }

    // ADMIN

    @GetMapping("/admin/reservations")
    public ApiResponse<PageResponse<AdminReservationResponse>> getReservationsForAdmin(
            @PageableDefault Pageable pageable
    ) {
        Page<AdminReservationResponse> page = reservationService.getReservationsForAdmin(pageable);
        return ApiResponse.success(PageResponse.of(page));
    }

    @GetMapping("/admin/reservations/{reservationCode}")
    public ApiResponse<AdminReservationResponse> getReservationDetailForAdmin(
            @PathVariable Long reservationCode
    ) {
        AdminReservationResponse response = reservationService.getReservationDetailForAdmin(reservationCode);
        return ApiResponse.success(response);
    }
}
