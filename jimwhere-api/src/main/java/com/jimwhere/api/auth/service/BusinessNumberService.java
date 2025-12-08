package com.jimwhere.api.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jimwhere.api.auth.dto.BusinessValidationRequest;
import com.jimwhere.api.auth.dto.BusinessValidationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessNumberService {

    @Value("${data.go.kr.service-key}")
    private String serviceKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 통합 서비스 (진위확인 + 휴폐업조회)
    public BusinessValidationResponse validateAndGetInfo(BusinessValidationRequest request) {

        boolean valid = validateBusiness(request);

        if (!valid) {
            return BusinessValidationResponse.builder()
                    .valid(false)
                    .bno(request.getBno())
                    .pName(request.getPName())
                    .startDt(request.getStartDt())
                    .build();
        }

        JsonNode status = getBusinessStatus(request.getBno());

        return BusinessValidationResponse.builder()
                .valid(true)
                .bno(request.getBno())
                .pName(request.getPName())
                .startDt(request.getStartDt())
                .taxType(asText(status, "tax_type"))
                .industryName(asText(status, "industry_nm"))
                .businessName(asText(status, "biz_condition"))
                .build();
    }


    // 진위확인 (대표자 + 개업일자 + 사업자번호)
    public boolean validateBusiness(BusinessValidationRequest request) {

        String url =
                "https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=" + serviceKey;

        String body = """
                {
                  "businesses": [
                    {
                      "b_no": "%s",
                      "start_dt": "%s",
                      "p_nm": "%s"
                    }
                  ]
                }
                """.formatted(
                request.getBno(),
                request.getStartDt(),
                request.getPName()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        log.info("[VALIDATE REQUEST] {}", body);

        String response = restTemplate.postForObject(url, entity, String.class);

        log.info("[VALIDATE RESPONSE RAW] {}", response);

        return parseValidation(response);
    }


    // 휴폐업 조회 (사업자번호만 필요)
    public JsonNode getBusinessStatus(String bno) {

        String url = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + serviceKey;

        ObjectNode root = objectMapper.createObjectNode();
        ArrayNode arr = objectMapper.createArrayNode();

        arr.add(bno); //객체가 아니라 문자열 배열
        root.set("b_no", arr);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(root.toString(), headers);

        log.info("STATUS REQUEST = {}", root.toString());

        String response = restTemplate.postForObject(url, entity, String.class);

        log.info("STATUS RESPONSE = {}", response);

        JsonNode result = parseJson(response);
        return result.get("data").get(0);
    }


    /* =================== 공통 유틸 =================== */

    private JsonNode parseJson(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            log.error(" JSON 오류: {}", json);
            throw new RuntimeException("JSON 오류", e);
        }
    }

    private boolean parseValidation(String json) {
        JsonNode root = parseJson(json);
        JsonNode validNode = root.get("data").get(0).get("valid");

        log.info(" [VALID NODE] {}", validNode);

        return validNode != null && validNode.asInt() == 1;
    }

    private String asText(JsonNode node, String key) {
        return node.has(key) ? node.get(key).asText() : null;
    }
}
