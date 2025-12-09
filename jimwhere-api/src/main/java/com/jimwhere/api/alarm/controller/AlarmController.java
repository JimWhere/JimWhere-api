package com.jimwhere.api.alarm.controller;

import com.jimwhere.api.alarm.domain.Alarm;
import com.jimwhere.api.alarm.domain.AlarmType;
import com.jimwhere.api.alarm.service.AlarmService;
import com.jimwhere.api.global.model.ApiResponse;
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
    public ApiResponse<List<Alarm>> getAll(@RequestParam Long userId) {
        return ApiResponse.success(alarmService.getAllAlarms(userId));
    }

    /* 미확인 알람 조회 */
    @GetMapping("/unread")
    public ApiResponse<List<Alarm>> getUnread(@RequestParam Long userId) {
        return ApiResponse.success(alarmService.getUnreadAlarms(userId));
    }

    /* 미확인 알람 갯수(뱃지) */
    @GetMapping("/unread/count")
    public ApiResponse<Long> getUnreadCount(@RequestParam Long userId) {
        return ApiResponse.success(alarmService.getUnreadCount(userId));
    }

    /* 타입별 알람 조회 */
    @GetMapping("/type")
    public ApiResponse<List<Alarm>> getByType(@RequestParam Long userId,
                                              @RequestParam AlarmType type) {
        return ApiResponse.success(alarmService.getAlarmsByType(userId, type));
    }

    /* 알람 읽음 처리 */
    @PatchMapping("/read/{alarmId}")
    public ApiResponse<Void> markAsRead(@PathVariable Long alarmId) {
        alarmService.markAsRead(alarmId);
        return ApiResponse.success(null);
    }

    /* 전체 알람 읽음 처리 */
    @PatchMapping("/readall")
    public ApiResponse<Void> markAllAsRead(@RequestParam Long userId) {
        alarmService.markAllAsRead(userId);
        return ApiResponse.success(null);
    }

    /* 알람 삭제 */
    @DeleteMapping("/delete/{alarmId}")
    public ApiResponse<Void> delete(@PathVariable Long alarmId) {
        alarmService.delete(alarmId);
        return ApiResponse.success(null);
    }

    /* 전체 알람 삭제 */
    @DeleteMapping("/deleteall")
    public ApiResponse<Void> deleteAll(@RequestParam Long userId) {
        alarmService.deleteAll(userId);
        return ApiResponse.success(null);
    }
}