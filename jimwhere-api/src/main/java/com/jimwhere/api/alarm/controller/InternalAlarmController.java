package com.jimwhere.api.alarm.controller;

import com.jimwhere.api.alarm.domain.Alarm;
import com.jimwhere.api.alarm.dto.AlarmCreateRequest;
import com.jimwhere.api.alarm.service.AlarmService;
import com.jimwhere.api.global.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name="내부 알림 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/alarms")
public class InternalAlarmController {

    private final AlarmService alarmService;

    /* 알람 생성 (내부 서비스 전용) */
    @Operation(
        summary = "알람 생성 (내부 서비스 전용)", description = "알람을 생성하고 전송하는 api"
    )
    @PostMapping
    public ApiResponse<Alarm> create(@RequestBody AlarmCreateRequest request) {
        return ApiResponse.success(alarmService.createAlarm(request));
    }
}