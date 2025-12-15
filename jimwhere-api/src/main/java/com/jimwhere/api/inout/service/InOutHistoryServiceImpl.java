package com.jimwhere.api.inout.service;


import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.access.domain.AccessResult;
import com.jimwhere.api.box.domain.Box;
import com.jimwhere.api.box.repository.BoxRepository;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.inout.domain.InOutHistory;
import com.jimwhere.api.inout.domain.InOutType;
import com.jimwhere.api.inout.dto.request.UpdateInOutHistoryRequest;
import com.jimwhere.api.inout.dto.response.InOutHistoryAllResponse;
import com.jimwhere.api.inout.dto.response.InOutHistoryResponse;
import com.jimwhere.api.inout.repository.InOutHistoryRepository;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InOutHistoryServiceImpl implements InOutHistoryService {
  private final InOutHistoryRepository inOutHistoryRepository;
  private final UserRepository userRepository;
  private final BoxRepository boxRepository;


  @Override
  @Transactional
  public String updateInOutHistory(Long inOutHistoryCode, UpdateInOutHistoryRequest request) {
    InOutHistory inOutHistory = inOutHistoryRepository.findById(inOutHistoryCode)
        .orElseThrow(() -> new CustomException(ErrorCode.INOUT_HISTORY_NOT_FOUND));

    Long boxCode = inOutHistory.getBox().getBoxCode();
    Long inOutQuantity = inOutHistory.getInOutQuantity();
    Long updateInOutQuantity = request.getInOutQuantity();
    Long result = Math.abs(inOutQuantity - updateInOutQuantity);

    AccessHistory accessHistory = inOutHistory.getAccessHistory();
    if (AccessResult.N.equals(accessHistory.getAccessResult())) {
      throw new CustomException(ErrorCode.ACCESS_HISTORY_RESULT_NOT_Y);
    }

    Box box = boxRepository.findById(boxCode)
        .orElseThrow(() -> new CustomException(ErrorCode.BOX_NOT_FOUND));

    if (!inOutHistory.getInOutType().equals(request.getInOutType())) {


      if (request.getInOutType() == InOutType.IN && request.getInOutName().equals(box.getBoxContent())) {
        box.addCurrentCount(inOutQuantity + updateInOutQuantity);

      } else if (request.getInOutType() == InOutType.OUT && request.getInOutName().equals(box.getBoxContent())) {
        long need = inOutQuantity + updateInOutQuantity;
        if (box.getBoxCurrentCount() < need) {
          throw new CustomException(ErrorCode.INVALID_BOX_COUNT);
        }
        box.subtractCurrentCount(need);

      } else if (request.getInOutType() == InOutType.IN) {
        box.updateBoxContentCount(request.getInOutName(), request.getInOutQuantity());

      } else {
        throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT, "출고할 물품이 없습니다.");
      }
    }


    else {

      if (request.getInOutType() == InOutType.IN && request.getInOutName().equals(box.getBoxContent())) {
        if (inOutQuantity < updateInOutQuantity) {
          box.addCurrentCount(result);
        } else {
          if (box.getBoxCurrentCount() < result) {
            throw new CustomException(ErrorCode.INVALID_BOX_COUNT);
          }
          box.subtractCurrentCount(result);
        }


      } else if (request.getInOutType() == InOutType.OUT && request.getInOutName().equals(box.getBoxContent())) {
        if (inOutQuantity >= updateInOutQuantity) {

          box.addCurrentCount(result);
        } else {

          if (box.getBoxCurrentCount() < result) {
            throw new CustomException(ErrorCode.INVALID_BOX_COUNT);
          }
          box.subtractCurrentCount(result);
        }

      } else if (request.getInOutType() == InOutType.IN) {
        box.updateBoxContentCount(request.getInOutName(), request.getInOutQuantity());
      } else {
        throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT, "출고할 물품이 없습니다.");
      }
    }

    inOutHistory.updateInOutHistory(
        request.getInOutType(),
        request.getInOutName(),
        request.getInOutQuantity()
    );

    return "재고 사항 수정";
  }


  @Override
  public Page<InOutHistoryResponse> findInOutHistoryList(      Long roomCode,
       String boxName,
      String userName,
      InOutType inOutType,
     String inOutName,
     Long inOutHistoryCode,Pageable pageable) {

    Long userCode=userRepository.findByUserId(userName)
        .orElseThrow(()->new CustomException(ErrorCode.INVALID_USER_ID)).getUserCode();

    Page<InOutHistoryResponse> page =
        inOutHistoryRepository.findUser( roomCode,
             boxName,
            userCode,
             inOutType,
              inOutName,
            inOutHistoryCode,pageable);
    return page;
  }
  @Override
  public Page<InOutHistoryAllResponse> findInOutHistoryListAll(  Long roomCode,
      String boxName,
      String userId,Pageable pageable) {
    Page<InOutHistoryAllResponse> page =
        inOutHistoryRepository.findAllWithUser(roomCode,boxName,userId,pageable);
    return page;
  }
}
