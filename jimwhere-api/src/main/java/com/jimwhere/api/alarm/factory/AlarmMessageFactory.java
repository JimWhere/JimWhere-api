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
                    "출입이 발생했습니다.";

            case MOVE_IN_DUE ->
                    "입주일 입니다.";

            case EXPIRATION_D3 ->
                    "계약 만기 3일 전입니다. 퇴실을 준비하시거나 관리자에게 문의 바랍니다.";

            default -> "새로운 알림이 도착했습니다.";
        };
    }
}
