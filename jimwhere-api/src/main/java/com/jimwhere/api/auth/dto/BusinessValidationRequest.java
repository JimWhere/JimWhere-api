package com.jimwhere.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BusinessValidationRequest {
    @JsonProperty("bno")
    private String bno;       // 사업자 등록번호
    @JsonProperty("startDt")
    private String startDt;   // 개업일자 (YYYYMMDD)
    @JsonProperty("pName")
    private String pName;     // 대표자명
}