package com.jimwhere.api.payment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TossInitResponse {

    private String orderId;
    private Long amount;
    private String orderName;
    private String clientKey;
    private String successUrl;
    private String failUrl;
}
