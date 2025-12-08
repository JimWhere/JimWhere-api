package com.jimwhere.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QrVerifyResponse {
    private Long accessHistoryId;  // 출입기록 PK
    private Long userId;
    private Long roomId;
    private String status;  // "AUTHORIZED"
}
