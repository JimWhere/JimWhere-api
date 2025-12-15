package com.jimwhere.api.reservation.service;

import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.reservation.domain.Reservation;
import com.jimwhere.api.reservation.dto.request.ReservationCreateRequest;
import com.jimwhere.api.reservation.dto.request.ReservationRangeDto;
import com.jimwhere.api.reservation.dto.response.AdminReservationResponse;
import com.jimwhere.api.reservation.dto.response.DashboardReservationDto;
import com.jimwhere.api.reservation.dto.response.ReservationResponse;
import com.jimwhere.api.reservation.repository.ReservationRepository;
import com.jimwhere.api.room.domain.Room;
import com.jimwhere.api.room.repository.RoomRepository;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    // USER
    // 예약 겹침 여부 확인 메소드
    public boolean existsOverlap(Long roomCode, LocalDateTime startAt, LocalDateTime endAt) {

        // 시작일자가 끝일자보다 앞서있어야하는 유효성 검증
        if (startAt == null || endAt == null ||
                !startAt.isBefore(endAt)) {
                throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        return reservationRepository
                .existsByRoomRoomCodeAndStartAtLessThanAndEndAtGreaterThan(roomCode, endAt, startAt);
    }

    // 기존 예약 일정 확인 메소드
    @Transactional(readOnly = true)
    public List<ReservationRangeDto> findReservationsForRoomInRange(Long roomCode, LocalDateTime from, LocalDateTime to) {
        List<Reservation> list = reservationRepository.findReservationsOverlappingRange(roomCode, from, to);
        return list.stream()
                .map(r -> new ReservationRangeDto(r.getStartAt(), r.getEndAt()))
                .collect(Collectors.toList());
        }
    

    @Transactional
    public ReservationResponse createReservation(String username, ReservationCreateRequest request) {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_USER_ID.getMessage()));

        Room room = roomRepository.findById(request.getRoomCode())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_REQUEST.getMessage()));

        // 멱등성: 동일 요청이면 기존 예약 반환
        return reservationRepository
                .findByUserAndRoomAndStartAtAndEndAt(user, room, request.getStartAt(), request.getEndAt())
                .map(ReservationResponse::from)
                .orElseGet(() -> {
                    Reservation reservation = Reservation.create(
                            user, room, request.getStartAt(), request.getEndAt(), request.getReservationAmount()
                    );
                    reservationRepository.save(reservation);
                    return ReservationResponse.from(reservation);
                });
    }


    public Page<ReservationResponse> getMyReservations(String username, Pageable pageable) {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_USER_ID.getMessage()));
        Page<Reservation> page = reservationRepository.findByUser(user, pageable);
        return page.map(ReservationResponse::from);
    }

    public ReservationResponse getMyReservationDetail(String username, Long reservationCode) {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_USER_ID.getMessage()));
        Reservation reservation = reservationRepository.findByReservationCodeAndUser(reservationCode, user)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_REQUEST.getMessage()));
        return ReservationResponse.from(reservation);
    }


    //ADMIN
    public Page<AdminReservationResponse> getReservationsForAdmin(Pageable pageable) {
        Page<Reservation> page = reservationRepository.findAll(pageable);
        return page.map(AdminReservationResponse::from);
    }

    public AdminReservationResponse getReservationDetailForAdmin(Long reservationCode) {
        Reservation reservation = reservationRepository.findById(reservationCode)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_REQUEST.getMessage()));
        return AdminReservationResponse.from(reservation);
    }


    // 관리자 대시보드용 예약 현황 뽑아오기
    public List<DashboardReservationDto> getLatestReservations(int limit) {

        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "startAt"));

        Page<Reservation> page = reservationRepository.findAll(pageable);

        return page.getContent().stream()
                .map(reservation -> {
                    long days = ChronoUnit.DAYS.between(
                            reservation.getStartAt().toLocalDate(),
                            reservation.getEndAt().toLocalDate()
                    );

                    return DashboardReservationDto.builder()
                            .reservationCode(reservation.getReservationCode())
                            .roomName(reservation.getRoom().getRoomName())
                            .startAt(reservation.getStartAt())
                            .endAt(reservation.getEndAt())
                            .days(days)
                            .build();
                })
                .toList();
    }



}