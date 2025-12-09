package com.jimwhere.api.alarm.factory;

import com.jimwhere.api.alarm.domain.AlarmType;
import org.springframework.stereotype.Component;

@Component
public class AlarmUrlFactory {

    public String create(AlarmType type, Long targetId) {

        return switch (type) {

            /* 문의 디테일 페이지로 이동 */
            case INQUIRY_ANSWER ->
                    "/api/v1/inquiries/" + targetId;

            /* 입출고/재고이벤트(예: 입고 상세, 작업 내역 상세)
            * TODO : 실 주소 다시 넣어야함 */
            case ENTRY_EVENT ->
                    "/api/v1/" + targetId;

            /* 입주일 도래 → 입주 예약 상세 */
            case MOVE_IN_DUE ->
                    "/api/v1/user/reservations/" + targetId;

            /* 만기 3일 전 → 입주 예약 상세 */
            case EXPIRATION_D3 ->
                    "/api/v1/user/reservations/" + targetId;

            default -> "/";
        };
    }
}
