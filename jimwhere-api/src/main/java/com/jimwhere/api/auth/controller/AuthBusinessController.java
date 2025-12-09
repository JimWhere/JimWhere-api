package com.jimwhere.api.auth.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.jimwhere.api.auth.dto.BusinessValidationRequest;
import com.jimwhere.api.auth.dto.BusinessValidationResponse;
import com.jimwhere.api.auth.service.BusinessNumberService;
import com.jimwhere.api.global.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthBusinessController {

    private final BusinessNumberService businessNumberService;


    // 사업자 진위확인 (대표자 + 개업일자 + 사업자번호)
    @PostMapping("/business/validate")
    public ResponseEntity<ApiResponse<Boolean>> validateBusiness(
            @RequestBody BusinessValidationRequest request
    ) {
        boolean valid = businessNumberService.validateBusiness(request);
        return ResponseEntity.ok(ApiResponse.success(valid));
    }


     //휴폐업 조회 (사업자번호만 필요)
     @PostMapping("/business/status")
     public ResponseEntity<ApiResponse<JsonNode>> getStatus(@RequestBody Map<String, String> request) {
         String bno = request.get("bno");
         JsonNode result = businessNumberService.getBusinessStatus(bno);
         return ResponseEntity.ok(ApiResponse.success(result));
     }




    //통합: 진위확인 + 정상영업조회
    @PostMapping("/business/check")
    public ResponseEntity<ApiResponse<BusinessValidationResponse>> checkBusiness(
            @RequestBody BusinessValidationRequest request
    ) {
        BusinessValidationResponse result = businessNumberService.validateAndGetInfo(request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
