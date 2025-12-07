package com.jimwhere.api.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusinessValidationResponse {

    private boolean valid;        // 진위 여부
    private String bno;           // 사업자등록번호
    private String pName;         // 대표자명
    private String startDt;       // 개업일자

    // 추가 정보(status API 결과)
    private String taxType;       // 과세유형
    private String industryName;  // 업태명
    private String businessName;  // 업종명


}
