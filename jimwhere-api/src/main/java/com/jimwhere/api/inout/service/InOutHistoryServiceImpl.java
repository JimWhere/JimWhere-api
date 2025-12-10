package com.jimwhere.api.inout.service;


import com.jimwhere.api.box.domain.Box;
import com.jimwhere.api.box.repository.BoxRepository;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.inout.domain.InOutHistory;
import com.jimwhere.api.inout.domain.InOutType;
import com.jimwhere.api.inout.dto.request.UpdateInOutHistoryRequest;
import com.jimwhere.api.inout.dto.response.InOutHistoryResponse;
import com.jimwhere.api.inout.repository.InOutHistoryRepository;
import com.jimwhere.api.user.domain.User;
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
    InOutHistory inOutHistory=inOutHistoryRepository.findById(inOutHistoryCode)
        .orElseThrow(()-> new CustomException(ErrorCode.INOUT_HISTORY_NOT_FOUND));
    Long boxCode=inOutHistory.getBox().getBoxCode();
    Box box= boxRepository.findById(boxCode).orElseThrow(()-> new CustomException(ErrorCode.BOX_NOT_FOUND));

    if(request.getInOutType()== InOutType.IN&& request.getInOutName().equals(box.getBoxContent())){
      box.addCurrentCount(request.getInOutQuantity());
    }else if(request.getInOutType()== InOutType.OUT&& request.getInOutName().equals(box.getBoxContent())){
      box.subtractCurrentCount(request.getInOutQuantity());
    }else if(request.getInOutType()== InOutType.IN){
      box.updateBoxContentCount(request.getInOutName(), request.getInOutQuantity());
    }
    else {throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT,"출고할 물품이 없습니다.");}
    inOutHistory.updateInOutHistory(request.getInOutType(),request.getInOutName(),request.getInOutQuantity());
    return "재고 사항 수정";
  }

  @Override
  public Page<InOutHistoryResponse> findInOutHistoryList(Pageable pageable, String userName) {
    User user=userRepository.findByUserId(userName)
        .orElseThrow(()->new CustomException(ErrorCode.INVALID_USER_ID));

    Page<InOutHistory> page =
        inOutHistoryRepository.findByAccessHistory_User(user, pageable);
    return page.map(InOutHistoryResponse::from);
  }
  @Override
  public Page<InOutHistoryResponse> findInOutHistoryListAll(Pageable pageable) {
    Page<InOutHistory> page =
        inOutHistoryRepository.findAll( pageable);
    return page.map(InOutHistoryResponse::from);
  }
}
