package com.jimwhere.api.auth.service;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SmsService {

    private final DefaultMessageService messageService;
    private final RedisTemplate<String, String> redisTemplate;

    public SmsService(
            @Value("${coolsms.api-key}") String apiKey,
            @Value("${coolsms.api-secret}") String apiSecret,
            RedisTemplate<String, String> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;

        this.messageService = NurigoApp.INSTANCE.initialize(
                apiKey,
                apiSecret,
                "https://api.coolsms.co.kr"
        );
    }

    // 인증번호 생성 + 저장 + 문자발송
    public void sendVerificationCode(String phone) {

        // 6자리 랜덤 코드 생성
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // Redis 저장 (5분 TTL)
        redisTemplate.opsForValue().set("sms:" + phone, code, 5, TimeUnit.MINUTES);

        // 문자 생성
        Message message = new Message();
        message.setFrom("01082802980");
        message.setTo(phone);
        message.setText(
                        "[JimWhere 인증]\n" +
                        "----------------------\n" +
                        "인증번호: " + code + "\n" +
                        "유효시간: 5분\n" +
                        "----------------------\n");

        try {
            messageService.send(message);
        } catch (Exception e) {
            throw new RuntimeException("문자 전송 실패: " + e.getMessage(), e);
        }
    }

    // 인증번호 검증
    public boolean verifyCode(String phone, String inputCode) {

        String saved = redisTemplate.opsForValue().get("sms:" + phone);

        if (saved == null) return false; // 만료 또는 존재 X

        return saved.equals(inputCode);
    }
}
