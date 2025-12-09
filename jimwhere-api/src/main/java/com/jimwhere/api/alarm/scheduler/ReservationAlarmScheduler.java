package com.jimwhere.api.alarm.scheduler;

import com.jimwhere.api.alarm.domain.AlarmType;
import com.jimwhere.api.alarm.dto.AlarmCreateRequest;
import com.jimwhere.api.alarm.service.AlarmService;
import com.jimwhere.api.reservation.domain.Reservation;
import com.jimwhere.api.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationAlarmScheduler {

    private final ReservationRepository reservationRepository;
    private final AlarmService alarmService;

    /* 매일 00:00 - 내일 입주 예정 알람 생성 (MOVE_IN_DUE) */
    @Scheduled(cron = "0 0 0 * * *")
    public void sendMoveInDueAlarms() {
        LocalDate targetDate = LocalDate.now().plusDays(1);
        LocalDateTime from = targetDate.atStartOfDay();
        LocalDateTime to = targetDate.atTime(23, 59, 59);

        List<Reservation> reservations =
                reservationRepository.findByStartAtBetween(from, to);

        if (reservations.isEmpty()) {
            log.debug("대상 예약 없음: {}", targetDate);
            return;
        }

        log.info("[MOVE_IN_DUE] {}건 예약에 대해 입주 알람 생성", reservations.size());

        reservations.forEach(reservation -> {
            AlarmCreateRequest request = new AlarmCreateRequest(
                    reservation.getUser().getUserCode(),   /* receiverUserId */
                    AlarmType.MOVE_IN_DUE,
                    reservation.getReservationCode()       /* targetId */
            );
            alarmService.createAlarm(request);
        });
    }

    /* 매일 00:00 - 만기 3일 전 알람 생성 (EXPIRATION_D3) */
    @Scheduled(cron = "0 0 0 * * *")
    public void sendExpirationD3Alarms() {
        LocalDate targetDate = LocalDate.now().plusDays(3);
        LocalDateTime from = targetDate.atStartOfDay();
        LocalDateTime to = targetDate.atTime(23, 59, 59);

        List<Reservation> reservations =
                reservationRepository.findByEndAtBetween(from, to);

        if (reservations.isEmpty()) {
            log.debug("대상 예약 없음: {}", targetDate);
            return;
        }

        log.info("[EXPIRATION_D3] {}건 예약에 대해 만기 3일 전 알람 생성", reservations.size());

        reservations.forEach(reservation -> {
            AlarmCreateRequest request = new AlarmCreateRequest(
                    reservation.getUser().getUserCode(),
                    AlarmType.EXPIRATION_D3,
                    reservation.getReservationCode()
            );
            alarmService.createAlarm(request);
        });
    }
}
