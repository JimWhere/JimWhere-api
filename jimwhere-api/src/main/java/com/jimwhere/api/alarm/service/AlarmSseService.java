package com.jimwhere.api.alarm.service;

import com.jimwhere.api.alarm.domain.Alarm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmSseService {

    /* 기본 타임아웃 (1시간) */
    private static final Long DEFAULT_TIMEOUT = 60L * 60L * 1000L;

    /* 유저별 SSE 연결 관리 */
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    /* SSE 구독 등록 */
    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        emitters.put(userId, emitter);

        emitter.onCompletion(() -> {
            emitters.remove(userId);
            log.debug("SSE 완료, 유저: {}", userId);
        });

        emitter.onTimeout(() -> {
            emitters.remove(userId);
            log.debug("SSE 타임아웃, 유저: {}", userId);
        });

        emitter.onError(e -> {
            emitters.remove(userId);
            log.debug("SSE 에러, 유저: {}, error: {}", userId, e.getMessage());
        });

        /* 최초 더미 이벤트 전송 (연결 확인용) */
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected"));
        } catch (IOException e) {
            emitters.remove(userId);
            log.warn("SSE 초기 전송 실패, 유저: {}", userId, e);
        }

        return emitter;
    }

    /* 알람 발생 시 SSE 전송 */
    public void sendAlarm(Alarm alarm) {
        Long userId = alarm.getUser().getUserCode(); /* User 엔티티 PK에 맞게 수정 */

        SseEmitter emitter = emitters.get(userId);
        if (emitter == null) {
            log.debug("SSE 구독 없음, 알람만 저장. 유저: {}", userId);
            return;
        }

        try {
            emitter.send(
                    SseEmitter.event()
                            .name("alarm")
                            .data(alarm)   /* 필요 시 AlarmResponse DTO로 변환해서 보내도 됨 */
            );
        } catch (IOException e) {
            emitters.remove(userId);
            log.warn("SSE 알람 전송 실패, 유저: {}", userId, e);
        }
    }
}
