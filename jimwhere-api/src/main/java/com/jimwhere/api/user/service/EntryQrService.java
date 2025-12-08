package com.jimwhere.api.user.service;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.access.repository.AccessHistoryRepository;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.global.model.QRCodeGenerator;
import com.jimwhere.api.user.dto.reqeust.EntryAuthData;
import com.jimwhere.api.user.dto.reqeust.QrIssueRequest;
import com.jimwhere.api.user.dto.response.QrIssueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntryQrService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final QRCodeGenerator qrCodeGenerator;
    private final AccessHistoryRepository accessHistoryRepository;

    private static final Duration PENDING_TTL = Duration.ofHours(24);

    public QrIssueResponse issueQr(Long accessHistoryId, Long userId, Long roomId) {

        // 1) QR Token 생성
        String qrToken = "ENTRY:" + accessHistoryId + ":" + UUID.randomUUID();
        String key = "entry:auth:" + qrToken;

        // 2) DB에 visitCode 업데이트
        AccessHistory history = accessHistoryRepository.findById(accessHistoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST,"해당정보가 없습니다"));

        history.updateVisitCode(qrToken);
        accessHistoryRepository.save(history);

        // 3) Redis 저장 (PENDING)
        EntryAuthData data = EntryAuthData.builder()
                .accessHistoryId(accessHistoryId)
                .userId(userId)
                .roomId(roomId)
                .status("PENDING")
                .build();

        redisTemplate.opsForValue().set(key, data, PENDING_TTL);

        // 4) QR 이미지 생성
        byte[] qrImage = qrCodeGenerator.generateQRCode(qrToken);

        return new QrIssueResponse(qrToken, qrImage);
    }





    public record PreviewQr(String qrToken, byte[] qrImage) {}

    public PreviewQr generatePreviewQr(Long accessHistoryId) throws Exception {

        // 1) DB 조회 (없는 ID면 오류)
        AccessHistory history = accessHistoryRepository.findById(accessHistoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST, "해당 출입 기록이 없습니다."));

        // 2) 발급된 QR 토큰이 있는지 확인
        if (history.getVisitCode() == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST, "이 출입 기록에는 발급된 QR이 없습니다.");
        }

        String qrToken = history.getVisitCode();

        // 3) QR 이미지 생성
        byte[] qrImage = qrCodeGenerator.generateQRCode(qrToken);

        return new PreviewQr(qrToken, qrImage);
    }


}
