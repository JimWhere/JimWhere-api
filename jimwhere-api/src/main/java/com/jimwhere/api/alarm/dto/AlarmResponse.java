package com.jimwhere.api.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AlarmResponse {
    private Long id;
    private String type;
    private String title;
    private String message;
    private String createdAt; // ISO or yyyy-MM-dd HH:mm
    private boolean isRead;
}
