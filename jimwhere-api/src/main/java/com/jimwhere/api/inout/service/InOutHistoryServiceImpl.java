package com.jimwhere.api.inout.service;

import com.jimwhere.api.inout.domain.InOutHistory;
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


  @Override
  @Transactional
  public String updateInOutHistory(Long inOutHistoryCode, UpdateInOutHistoryRequest request) {
    InOutHistory inOutHistory=inOutHistoryRepository.findById(inOutHistoryCode).orElseThrow();
    if(request.getInOutType()!=null){
      inOutHistory.updateInOutType(request.getInOutType());
    }
    if(request.getInOutName()!=null){
      inOutHistory.updateInOutName(request.getInOutName());
    }
    if(request.getInOutQuantity()!=null){
      inOutHistory.updateInOutQuantity(request.getInOutQuantity());
    }
    inOutHistoryRepository.save(inOutHistory);
    return "재고 사항 수정";
  }

  @Override
  public Page<InOutHistoryResponse> findInOutHistoryList(Pageable pageable, String userName) {
    User user=userRepository.findByUserId(userName).orElseThrow();
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
