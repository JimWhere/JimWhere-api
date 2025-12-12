package com.jimwhere.api.inquiry.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InquiryStatsResponse {
    private long total;      // 전체 문의 수
    private long completed;  // 처리 완료 수
    private long pending;    // 미완료 수
}
