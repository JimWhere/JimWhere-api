package com.jimwhere.api.alarm.domain;

public enum AlarmType {

    INQUIRY_ANSWER,      /* 문의 답변 알림 */

    ENTRY_EVENT,         /* 입출고, 재고 실사 작업, 기타 여러 이벤트 포함 */

    MOVE_IN_DUE,         /* 입주일 도래 */

    EXPIRATION_D3        /* 만기 3일 전 알림 */
}

