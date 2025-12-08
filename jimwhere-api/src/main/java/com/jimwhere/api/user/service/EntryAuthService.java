package com.jimwhere.api.user.service;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.access.domain.AccessResult;
import com.jimwhere.api.access.repository.AccessHistoryRepository;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.user.dto.reqeust.EntryAuthData;
import com.jimwhere.api.user.dto.reqeust.QrVerifyRequest;
import com.jimwhere.api.user.dto.response.QrVerifyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EntryAuthService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final AccessHistoryRepository accessHistoryRepository;

    private final static Duration AUTHORIZED_TTL = Duration.ofMinutes(30);

    public QrVerifyResponse verify(QrVerifyRequest req) {

        String key = "entry:auth:" + req.getToken();

        EntryAuthData data = (EntryAuthData) redisTemplate.opsForValue().get(key);

        if (data == null) {
            throw new CustomException(ErrorCode.INVALID_OR_EXPIRED_QR, "QR이 만료되었거나 존재하지 않습니다.");
        }

        // 최초 인증일 경우
        if ("PENDING".equals(data.getStatus())) {

            data.setStatus("AUTHORIZED");

            // ⬇ DB AccessHistory 업데이트
            AccessHistory history = accessHistoryRepository
                    .findById(data.getAccessHistoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST,"해당정보가 없습니다"));

            history.updateAccessSuccess(LocalDateTime.now());

            accessHistoryRepository.save(history);

            // Redis TTL = 30분 재설정
            redisTemplate.opsForValue().set(key, data, AUTHORIZED_TTL);
        }

        // 재입장일 경우: AUTHORIZED 상태 그대로 응답
        return new QrVerifyResponse(
                data.getAccessHistoryId(),
                data.getUserId(),
                data.getRoomId(),
                data.getStatus()
        );
    }
}
