package com.jimwhere.api.payment.client;

import com.jimwhere.api.payment.dto.request.TossConfirmRequest;
import com.jimwhere.api.payment.dto.response.TossConfirmResponse;
import com.jimwhere.api.payment.properties.TossPaymentProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


    // Toss Payments API 호출 클라이언트
@Component
@RequiredArgsConstructor
public class TossPaymentClient {

    private final TossPaymentProperties properties;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 결제 승인 요청
     * POST {baseUrl}/v1/payments/confirm
     * body: {paymentKey, orderId, amount}
     */
    public TossConfirmResponse confirmPayment(TossConfirmRequest request) {

        String url = properties.getBaseUrl() + "/v1/payments/confirm";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // secret-key: (Basic Auth 형식)
        headers.setBasicAuth(properties.getSecretKey(), "");

        HttpEntity<TossConfirmRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<TossConfirmResponse> response =
                restTemplate.exchange(url, HttpMethod.POST, entity, TossConfirmResponse.class);

        return response.getBody();
    }
}
