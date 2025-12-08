package com.jimwhere.api.alarm.service;

import com.jimwhere.api.alarm.domain.Alarm;
import com.jimwhere.api.alarm.domain.AlarmType;
import com.jimwhere.api.alarm.domain.IsRead;
import com.jimwhere.api.alarm.dto.AlarmCreateRequest;
import com.jimwhere.api.alarm.factory.AlarmUrlFactory;
import com.jimwhere.api.alarm.factory.AlarmMessageFactory;
import com.jimwhere.api.alarm.repository.AlarmRepository;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    private final AlarmUrlFactory alarmUrlFactory;
    private final AlarmMessageFactory alarmMessageFactory;

    /* 알람 생성 */
    @Transactional
    public Alarm createAlarm(AlarmCreateRequest request) {

        User receiver = getUser(request.getReceiverUserId());

        String link = alarmUrlFactory.create(request.getType(), request.getTargetId());
        String title = alarmMessageFactory.title(request.getType());
        String message = alarmMessageFactory.message(request.getType());

        Alarm alarm = Alarm.builder()
                .user(receiver)
                .type(request.getType())
                .title(title)
                .message(message)
                .link(link)
                .isRead(IsRead.N)
                .build();

        return alarmRepository.save(alarm);
    }

    /* 전체 알람 조회 */
    public List<Alarm> getAllAlarms(Long userId) {
        User user = getUser(userId);
        return alarmRepository.findByUserOrderByCreatedAtDesc(user);
    }

    /* 미확인 알람 조회 */
    public List<Alarm> getUnreadAlarms(Long userId) {
        User user = getUser(userId);
        return alarmRepository.findByUserAndIsReadOrderByCreatedAtDesc(user, IsRead.N);
    }

    /* 미확인 알람 개수 */
    public Long getUnreadCount(Long userId) {
        User user = getUser(userId);
        return alarmRepository.countByUserAndIsRead(user, IsRead.N);
    }

    /* 타입별 알람 조회 */
    public List<Alarm> getAlarmsByType(Long userId, AlarmType type) {
        User user = getUser(userId);
        return alarmRepository.findByUserAndType(user, type);
    }

    /* 알람 읽음 처리 */
    @Transactional
    public void markAsRead(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("알람이 존재하지 않습니다."));
        alarm.markAsRead();
    }

    /* 전체 알람 읽음 처리 */
    @Transactional
    public void markAllAsRead(Long userId) {
        List<Alarm> unread = getUnreadAlarms(userId);
        unread.forEach(Alarm::markAsRead);
    }

    /* 알람 삭제 */
    @Transactional
    public void delete(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("알람이 존재하지 않습니다."));
        alarm.softDelete();
    }

    /* 전체 알람 삭제 */
    @Transactional
    public void deleteAll(Long userId) {
        User user = getUser(userId);
        List<Alarm> alarms = alarmRepository.findByUserOrderByCreatedAtDesc(user);
        alarms.forEach(Alarm::softDelete);
    }

    /* 유저 조회 (공통) */
    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }
}
