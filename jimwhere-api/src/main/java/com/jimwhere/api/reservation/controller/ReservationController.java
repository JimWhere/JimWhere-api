package com.jimwhere.api.reservation.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.reservation.dto.request.ReservationCreateRequest;
import com.jimwhere.api.reservation.dto.request.ReservationRangeDto;
import com.jimwhere.api.reservation.dto.response.AdminReservationResponse;
import com.jimwhere.api.reservation.dto.response.DashboardReservationDto;
import com.jimwhere.api.reservation.dto.response.ReservationResponse;
import com.jimwhere.api.reservation.service.ReservationService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // ROOM
    // 특정 룸의 예약현황을 반환
    @GetMapping("/room/{roomCode}/reservations")
    public ResponseEntity<ApiResponse<List<ReservationRangeDto>>> getReservationsForRoom(
        @PathVariable Long roomCode,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        LocalDateTime fromAt = from.atStartOfDay();
        LocalDateTime toAt = to.atTime(LocalTime.MAX);
        List<ReservationRangeDto> ranges = reservationService.findReservationsForRoomInRange(roomCode, fromAt, toAt);
        return ResponseEntity.ok(ApiResponse.success(ranges));
    }

    // USER

    // 프론트가 예약 전 겹침 여부를 확인할 수 있는 GET 엔드포인트
    @GetMapping("/user/reservations/check")
    public ApiResponse<Boolean> checkOverlap(
        @RequestParam Long roomCode,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startAt,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAt
    ) {
        boolean overlapping = reservationService.existsOverlap(roomCode, startAt, endAt);
        System.out.println("CHECK debug: room=" + roomCode + " start=" + startAt + " end=" + endAt + " -> overlap=" + overlapping);
        return ApiResponse.success(overlapping);
    }
    
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


    // 관리자 대시보드용
    @GetMapping("/admin/reservations/latest")
    public ApiResponse<List<DashboardReservationDto>> getLatestReservations(
            @RequestParam(defaultValue = "3") int limit
    ) {
        return ApiResponse.success(reservationService.getLatestReservations(limit));
    }


}
