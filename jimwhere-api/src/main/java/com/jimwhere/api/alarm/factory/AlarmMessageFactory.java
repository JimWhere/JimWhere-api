package com.jimwhere.api.alarm.factory;

import com.jimwhere.api.alarm.domain.AlarmType;
import org.springframework.stereotype.Component;

@Component
public class AlarmMessageFactory {

    public String title(AlarmType type) {
        return switch (type) {

            case INQUIRY_ANSWER -> "문의 답변 등록";

            case ENTRY_EVENT -> "창고 작업 이벤트 발생";

            case MOVE_IN_DUE -> "입주일 도래 알림";

            case EXPIRATION_D3 -> "계약 만기 예정 알림";

            default -> "알림";
        };
    }

    public String message(AlarmType type) {
        return switch (type) {

            case INQUIRY_ANSWER ->
                    "문의하신 내용에 대한 답변이 등록되었습니다.";

            case ENTRY_EVENT ->
                    "입출고 또는 작업 이벤트가 발생했습니다.";

            case MOVE_IN_DUE ->
                    "입주 예정일이 다가오고 있습니다.";

            case EXPIRATION_D3 ->
                    "계약 만기 3일 전입니다. 연장 또는 퇴실 준비가 필요합니다.";

            default -> "새로운 알림이 도착했습니다.";
        };
    }
}
