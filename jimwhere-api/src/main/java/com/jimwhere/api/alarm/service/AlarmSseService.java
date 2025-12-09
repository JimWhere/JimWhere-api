package com.jimwhere.api.alarm.service;

import com.jimwhere.api.alarm.domain.Alarm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmSseService {

    /* 기본 타임아웃 (1시간) */
    private static final Long DEFAULT_TIMEOUT = 60L * 60L * 1000L;

    /* 유저별 SSE 연결 관리 */
    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    /* SSE 구독 등록 */
    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        emitters.compute(userId, (id, list) -> {
            List<SseEmitter> emittersByUser = list != null ? list : new CopyOnWriteArrayList<>();
            emittersByUser.add(emitter);
            return emittersByUser;
        });

        emitter.onCompletion(() -> removeEmitter(userId, emitter, "완료", false));

        emitter.onTimeout(() -> removeEmitter(userId, emitter, "타임아웃", true));

        emitter.onError(e -> removeEmitter(userId, emitter, "에러: " + (e != null ? e.getMessage() : ""), true));

        /* 최초 더미 이벤트 전송 (연결 확인용) */
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected"));
        } catch (IOException e) {
            removeEmitter(userId, emitter, "초기 전송 실패: " + e.getMessage(), true);
            log.warn("SSE 초기 전송 실패, 유저: {}", userId, e);
        }

        return emitter;
    }

    /* 알람 발생 시 SSE 전송 */
    public void sendAlarm(Alarm alarm) {
        Long userId = alarm.getUser().getUserCode(); /* User 엔티티 PK에 맞게 수정 */

        List<SseEmitter> userEmitters = emitters.get(userId);
        if (userEmitters == null || userEmitters.isEmpty()) {
            log.debug("SSE 구독 없음, 알람만 저장. 유저: {}", userId);
            return;
        }

        userEmitters.forEach(emitter -> {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("alarm")
                                .data(alarm)   /* 필요 시 AlarmResponse DTO로 변환해서 보내도 됨 */
                );
            } catch (IOException e) {
                removeEmitter(userId, emitter, "알람 전송 실패: " + e.getMessage(), true);
                log.warn("SSE 알람 전송 실패, 유저: {}", userId, e);
            }
        });
    }

    private void removeEmitter(Long userId, SseEmitter emitter, String reason, boolean complete) {
        List<SseEmitter> emittersByUser = emitters.get(userId);
        if (emittersByUser == null) {
            return;
        }

        emittersByUser.remove(emitter);
        if (complete) {
            completeQuietly(emitter);
        }
        log.debug("SSE {}: 유저: {}, 남은 연결 수: {}", reason, userId, emittersByUser.size());

        if (emittersByUser.isEmpty()) {
            emitters.remove(userId);
        }
    }

    private void completeQuietly(SseEmitter emitter) {
        try {
            emitter.complete();
        } catch (Exception e) {
            log.debug("SSE 종료 처리 중 예외 무시: {}", e.getMessage());
        }
    }
}
