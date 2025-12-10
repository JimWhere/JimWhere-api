package com.jimwhere.api.payment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TossInitRequest {

    private Long amount;      // 결제 금액
    private String orderName; // 주문명 (예: 짐웨어 a타입 1개월)

    // 지금은 userCode, reservationCode 안 씀
}
