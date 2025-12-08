package com.jimwhere.api.access.service;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.access.dto.request.CreateAccessHistoryRequest;
import com.jimwhere.api.access.repository.AccessHistoryRepository;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.dto.response.QrIssueResponse;
import com.jimwhere.api.user.repository.UserRepository;
import java.util.UUID;

import com.jimwhere.api.user.service.EntryQrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessHistoryServiceImpl implements AccessHistoryService {

    private final AccessHistoryRepository accessHistoryRepository;
    private final UserRepository userRepository;
    private final EntryQrService entryQrService;

    @Override
    public QrIssueResponse createAccessHistory(CreateAccessHistoryRequest request, String userName) {

        User user = userRepository.findByUserId(userName)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

        // 1) AccessHistory 생성
        AccessHistory history = AccessHistory.createAccessHistoryBuilder(
                request.isOwner(),
                request.visitPurpose(),
                null,
                user
        );

        AccessHistory saved = accessHistoryRepository.save(history);

        // 2) QR 발급을 EntryQrService에 위임 → 책임 분리
        return entryQrService.issueQr(
                saved.getAccessHistoryCode(),
                user.getUserCode(),
                request.roomId()
        );
    }
}
