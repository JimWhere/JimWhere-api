package com.jimwhere.api.alarm.controller;

import com.jimwhere.api.alarm.domain.Alarm;
import com.jimwhere.api.alarm.domain.AlarmType;
import com.jimwhere.api.alarm.service.AlarmService;
import com.jimwhere.api.alarm.service.AlarmSseService;
import com.jimwhere.api.global.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Tag(name = "알림 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarms")
public class AlarmController {

    private final AlarmService alarmService;
    private final AlarmSseService alarmSseService;

    /* 알람 SSE 스트림 연결 */
    @Operation(
        summary = "알람 SSE 스트림 연결", description = "SSE 연결을 통해 유저가 알람을 받을 수 있게 한다."
    )
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestParam Long userId) {
        return alarmSseService.subscribe(userId);
    }

    /* 전체 알람 목록 */
    @Operation(
        summary = "전체 알람 목록", description = "유저가 자신에게 온 알람을 전체 조회하는 api"
    )
    @GetMapping("/list")
    public ApiResponse<List<Alarm>> getAll(@RequestParam Long userId) {
        return ApiResponse.success(alarmService.getAllAlarms(userId));
    }

    /* 미확인 알람 조회 */
    @Operation(
        summary = "미확인 알람 조회", description = "유저가 확인하지 않은 알람 목록을 조회하는 api"
    )
    @GetMapping("/unread")
    public ApiResponse<List<Alarm>> getUnread(@RequestParam Long userId) {
        return ApiResponse.success(alarmService.getUnreadAlarms(userId));
    }

    /* 미확인 알람 갯수(뱃지) */
    @Operation(
        summary = "미확인 알람 갯수 표시", description = "유저가 미확인 알람 갯수를 확인할 수 있는 api"
    )
    @GetMapping("/unread/count")
    public ApiResponse<Long> getUnreadCount(@RequestParam Long userId) {
        return ApiResponse.success(alarmService.getUnreadCount(userId));
    }

    /* 타입별 알람 조회 */
    @Operation(
        summary = "알람 타입별 조회", description = "유저가 자신에게 온 알람을 타입별로 조회하는 api "
    )
    @GetMapping("/type")
    public ApiResponse<List<Alarm>> getByType(@RequestParam Long userId,
                                              @RequestParam AlarmType type) {
        return ApiResponse.success(alarmService.getAlarmsByType(userId, type));
    }

    /* 알람 읽음 처리 */
    @Operation(
        summary = "알람 읽음 처리", description = "유저가 알람을 읽으면 읽음으로 처리되는 api"
    )
    @PatchMapping("/read/{alarmId}")
    public ApiResponse<Void> markAsRead(@PathVariable Long alarmId) {
        alarmService.markAsRead(alarmId);
        return ApiResponse.success(null);
    }

    /* 전체 알람 읽음 처리 */
    @Operation(
        summary = "전체 알람 읽음 처리", description = "유저가 자신에게 온 전체 알람을 읽음으로 처리"
    )
    @PatchMapping("/readall")
    public ApiResponse<Void> markAllAsRead(@RequestParam Long userId) {
        alarmService.markAllAsRead(userId);
        return ApiResponse.success(null);
    }

    /* 알람 삭제 */
    @Operation(
        summary = "알람 삭제", description = "유저가 알람을 자신에게 온  선택해서 삭제하는 api"
    )
    @DeleteMapping("/delete/{alarmId}")
    public ApiResponse<Void> delete(@PathVariable Long alarmId) {
        alarmService.delete(alarmId);
        return ApiResponse.success(null);
    }

    /* 전체 알람 삭제 */
    @Operation(
        summary = "전체 알람 삭제", description = "유저가 자신에게 온 알람을 전체 삭제하는 api"
    )
    @DeleteMapping("/deleteall")
    public ApiResponse<Void> deleteAll(@RequestParam Long userId) {
        alarmService.deleteAll(userId);
        return ApiResponse.success(null);
    }
}