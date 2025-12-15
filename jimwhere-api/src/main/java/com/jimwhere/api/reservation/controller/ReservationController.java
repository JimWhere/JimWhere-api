package com.jimwhere.api.reservation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name="예약 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // ROOM
    // 특정 룸의 예약현황을 반환

  @Operation(
      summary = "특정 방 예약현황 리스트 조회", description = "특정 룸에 대한 예약현황 리스트 조회하는 api "
  )
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
    @Operation(
        summary = "예약 중복 확인 ", description = "방 예약 전 예약의 중복 여부를 확인할 수 있는 api"
    )
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

  @Operation(
      summary = "방 예약", description = "유저가 방을 예약하는 api"
  )
    @PostMapping("/user/reservations")
    public ApiResponse<ReservationResponse> createReservation(
            @RequestBody ReservationCreateRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        String username = user.getUsername();
        ReservationResponse response = reservationService.createReservation(username, request);
        return ApiResponse.success(response);
    }

  @Operation(
      summary = "유저 예약 리스트 조회", description = "유저가 예약한 리스트를 조회하는 api "
  )
    @GetMapping("/user/reservations")
    public ApiResponse<PageResponse<ReservationResponse>> getMyReservations(
            @AuthenticationPrincipal CustomUser user,
            @PageableDefault Pageable pageable
    ) {
        String username = user.getUsername();
        Page<ReservationResponse> page = reservationService.getMyReservations(username, pageable);
        return ApiResponse.success(PageResponse.of(page));
    }
  @Operation(
      summary = "유저 예약 상세 조회", description = "유저가 본인이 예약한 특정 예약을 상세조회하는 api"
  )
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

  @Operation(
      summary = "관리자 예약 리스트 조회", description = "관리자가 예약한 리스트를 조회하는 api"
  )
    @GetMapping("/admin/reservations")
    public ApiResponse<PageResponse<AdminReservationResponse>> getReservationsForAdmin(
            @PageableDefault Pageable pageable
    ) {
        Page<AdminReservationResponse> page = reservationService.getReservationsForAdmin(pageable);
        return ApiResponse.success(PageResponse.of(page));
    }
  @Operation(
      summary = "관리자 예약 상세 조회", description = "관리자가 특정 예약의 상세정보를 조회하는 api"
  )
    @GetMapping("/admin/reservations/{reservationCode}")
    public ApiResponse<AdminReservationResponse> getReservationDetailForAdmin(
            @PathVariable Long reservationCode
    ) {
        AdminReservationResponse response = reservationService.getReservationDetailForAdmin(reservationCode);
        return ApiResponse.success(response);
    }


    // 관리자 대시보드용
    @Operation(
        summary = "예약(관리자 대시보드용)", description = "관리자 대시보드에 표시될 예약정보 조회 api"
    )
    @GetMapping("/admin/reservations/latest")
    public ApiResponse<List<DashboardReservationDto>> getLatestReservations(
            @RequestParam(defaultValue = "3") int limit
    ) {
        return ApiResponse.success(reservationService.getLatestReservations(limit));
    }


}
