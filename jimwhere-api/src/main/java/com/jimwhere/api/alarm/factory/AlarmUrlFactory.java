package com.jimwhere.api.alarm.factory;

import com.jimwhere.api.alarm.domain.AlarmType;
import org.springframework.stereotype.Component;

@Component
public class AlarmUrlFactory {

    public String create(AlarmType type, Long targetId) {

        return switch (type) {

            // 문의 디테일 페이지로 이동
            case INQUIRY_ANSWER ->
                    "/inquiries/" + targetId;

            // 입출고/재고이벤트(예: 입고 상세, 작업 내역 상세)
            case ENTRY_EVENT ->
                    "/entries/" + targetId;

            // 입주일 도래 → 입주 예약 상세
            case MOVE_IN_DUE ->
                    "/reservations/" + targetId;

            // 만기 3일 전 → 계약 상세 페이지
            case EXPIRATION_D3 ->
                    "/contracts/" + targetId;

            default -> "/";
        };
    }
}
