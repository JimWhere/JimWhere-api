package com.jimwhere.api.alarm.controller;

import com.jimwhere.api.alarm.domain.Alarm;
import com.jimwhere.api.alarm.domain.AlarmType;
import com.jimwhere.api.alarm.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    /* 전체 알람 목록 */
    @GetMapping("/list")
    public List<Alarm> getAll(@RequestParam Long userId) {
        return alarmService.getAllAlarms(userId);
    }

    /* 미확인 알람 조회 */
    @GetMapping("/unread")
    public List<Alarm> getUnread(@RequestParam Long userId) {
        return alarmService.getUnreadAlarms(userId);
    }

    /* 미확인 알람 갯수(뱃지) */
    @GetMapping("/unread/count")
    public Long getUnreadCount(@RequestParam Long userId) {
        return alarmService.getUnreadCount(userId);
    }

    /* 타입별 알람 조회 */
    @GetMapping("/type")
    public List<Alarm> getByType(@RequestParam Long userId,
                                 @RequestParam AlarmType type) {
        return alarmService.getAlarmsByType(userId, type);
    }

    /* 알람 읽음 처리 */
    @PatchMapping("/read/{alarmId}")
    public void markAsRead(@PathVariable Long alarmId) {
        alarmService.markAsRead(alarmId);
    }

    /* 전체 알람 읽음 처리 */
    @PatchMapping("/readall")
    public void markAllAsRead(@RequestParam Long userId) {
        alarmService.markAllAsRead(userId);
    }

    /* 알람 삭제 */
    @DeleteMapping("/delete/{alarmId}")
    public void delete(@PathVariable Long alarmId) {
        alarmService.delete(alarmId);
    }

    /* 전체 알람 삭제 */
    @DeleteMapping("/deleteall")
    public void deleteAll(@RequestParam Long userId) {
        alarmService.deleteAll(userId);
    }
}