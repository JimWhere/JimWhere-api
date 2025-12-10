package com.jimwhere.api.access.service;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.access.domain.VisitPurpose;
import com.jimwhere.api.access.dto.request.CreateAccessHistoryRequest;
import com.jimwhere.api.access.dto.response.AccessHistoryDetailResponse;
import com.jimwhere.api.access.dto.response.AccessHistoryResponse;
import com.jimwhere.api.access.repository.AccessHistoryRepository;
import com.jimwhere.api.box.domain.Box;
import com.jimwhere.api.box.repository.BoxRepository;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.inout.domain.InOutHistory;
import com.jimwhere.api.inout.dto.request.CreateInOutHistoryRequest;
import com.jimwhere.api.inout.dto.response.InOutDetailResponse;
import com.jimwhere.api.inout.repository.InOutHistoryRepository;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.dto.response.QrIssueResponse;
import com.jimwhere.api.user.repository.UserRepository;

import com.jimwhere.api.user.service.EntryQrService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccessHistoryServiceImpl implements AccessHistoryService {

  private final AccessHistoryRepository accessHistoryRepository;
  private final UserRepository userRepository;
  private final EntryQrService entryQrService;
  private final BoxRepository boxRepository;
  private final InOutHistoryRepository inOutHistoryRepository;

  @Override
  public QrIssueResponse createAccessHistory(CreateAccessHistoryRequest request, String userName) {

    User user = userRepository.findByUserId(userName)
        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

    if(request.getVisitPurpose()!= VisitPurpose.ENTRY&&request.getVisitPurpose()!=VisitPurpose.INOUT){
      throw new CustomException(ErrorCode.INVALID_INCORRECT_FORMAT , "방문 목적을 ENTRY 혹은 INOUT 중에서 선택해주세요");
    }

    // 1) AccessHistory 생성
    AccessHistory history = AccessHistory.createAccessHistoryBuilder(
        request.getIsOwner(),
        request.getVisitPurpose(),
        null,
        request.getRoomCode(),
        user
    );
    for (CreateInOutHistoryRequest inOutHistoryRequest : request.getInOutHistoryRequestList()) {
        Box box=boxRepository.findById(inOutHistoryRequest.getBoxCode())
            .orElseThrow(()->new CustomException(ErrorCode.BOX_NOT_FOUND));
      InOutHistory inOutHistory = InOutHistory.createInOutHistory(
          inOutHistoryRequest.getInOutType(),
          inOutHistoryRequest.getInOutName(),
          inOutHistoryRequest.getInOutQuantity(),
          box
      );
      history.addInOutHistory(inOutHistory);
    }

    AccessHistory saved = accessHistoryRepository.save(history);

    // 2) QR 발급을 EntryQrService에 위임 → 책임 분리
   return entryQrService.issueQr(
        saved.getAccessHistoryCode(),
        user.getUserCode(),
        request.getRoomCode()
    );
  }

  @Override
  public QrIssueResponse updateAccessHistory(CreateAccessHistoryRequest request, String userName,
      Long accessHistoryCode) {

    User user = userRepository.findByUserId(userName)
        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

    AccessHistory accessHistory = accessHistoryRepository.findById(accessHistoryCode)
        .orElseThrow(()->new CustomException(ErrorCode.ACCESS_HISTORY_NOT_FOUND));

    accessHistory.updateBasic(request.getIsOwner(), request.getVisitPurpose());

    AccessHistory saved = accessHistoryRepository.save(accessHistory);

    return entryQrService.issueQr(
        saved.getAccessHistoryCode(),
        user.getUserCode(),
        request.getRoomCode()
    );
  }

  @Override
  public Page<AccessHistoryResponse> getAccessHistoryList(Pageable pageable,String userName) {
      User user=userRepository.findByUserId(userName).orElseThrow(
          ()->new CustomException(ErrorCode.INVALID_USER_ID)
      );
      Page<AccessHistory> page =
          accessHistoryRepository.findByUser(user, pageable);
      return page.map(AccessHistoryResponse::from);
    }
  @Override
  public Page<AccessHistoryResponse> getAccessHistoryListAll(Pageable pageable) {
    Page<AccessHistory> page =
        accessHistoryRepository.findAll(pageable);
    return page.map(AccessHistoryResponse::from);
  }

  @Transactional
  public AccessHistoryDetailResponse selectDetailByCode(Long code) {

    AccessHistory a = accessHistoryRepository.findById(code)
        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST, "존재하지 않는 기록입니다."));

    List<InOutDetailResponse> inOutList = inOutHistoryRepository.findDetailsByAccessHistory(a);

    return AccessHistoryDetailResponse.builder()
        .accessHistoryCode(a.getAccessHistoryCode())
        .isOwner(a.getIsOwner().name())
        .visitPurpose(a.getVisitPurpose().name())
        .visitCode(a.getVisitCode())
        .accessedAt(a.getAccessedAt())
        .accessResult(a.getAccessResult().name())
        .userName(a.getUser().getPName())
        .inOutList(inOutList)
        .build();
  }
}
