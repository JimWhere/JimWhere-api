package com.jimwhere.api.alarm.controller;

import com.jimwhere.api.alarm.domain.Alarm;
import com.jimwhere.api.alarm.dto.AlarmCreateRequest;
import com.jimwhere.api.alarm.service.AlarmService;
import com.jimwhere.api.global.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/alarms")
public class InternalAlarmController {

    private final AlarmService alarmService;

    /* 알람 생성 (내부 서비스 전용) */
    @PostMapping
    public ApiResponse<Alarm> create(@RequestBody AlarmCreateRequest request) {
        return ApiResponse.success(alarmService.createAlarm(request));
    }
}