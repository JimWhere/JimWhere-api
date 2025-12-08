package com.jimwhere.api.reservation.service;

import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.reservation.domain.Reservation;
import com.jimwhere.api.reservation.dto.request.ReservationCreateRequest;
import com.jimwhere.api.reservation.dto.response.AdminReservationResponse;
import com.jimwhere.api.reservation.dto.response.ReservationResponse;
import com.jimwhere.api.reservation.repository.ReservationRepository;
import com.jimwhere.api.room.domain.Room;
import com.jimwhere.api.room.repository.RoomRepository;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    // USER
    @Transactional
    public ReservationResponse createReservation(String username, ReservationCreateRequest request) {

        // username 기준으로 User 조회
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_USER_ID.getMessage()));

        Room room = roomRepository.findById(request.getRoomCode())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INVALID_REQUEST.getMessage()));

        Reservation reservation = Reservation.create(
                user,
                room,
                request.getStartAt(),
                request.getEndAt(),
                request.getReservationAmount()
        );

        reservationRepository.save(reservation);
        return ReservationResponse.from(reservation);
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
}
