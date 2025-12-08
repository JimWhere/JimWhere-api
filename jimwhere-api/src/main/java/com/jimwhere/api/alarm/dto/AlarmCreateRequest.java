package com.jimwhere.api.alarm.dto;

import com.jimwhere.api.alarm.domain.AlarmType;
import lombok.Getter;

@Getter
public class AlarmCreateRequest {

    private Long receiverUserId;   // 알람 받을 사용자
    private AlarmType type;        // 알람 타입
    private Long targetId;         // 알람 대상 리소스 PK
}
