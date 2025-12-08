package com.jimwhere.api.access.service;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.access.dto.request.CreateAccessHistoryRequest;
import com.jimwhere.api.access.repository.AccessHistoryRepository;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessHistoryServiceImpl implements AccessHistoryService {
  private final AccessHistoryRepository accessHistoryRepository;
  private final UserRepository userRepository;

  @Override
  public String createAccessHistory(CreateAccessHistoryRequest request, String userName) {
    if(request==null){
      throw new IllegalArgumentException("request is null");
    }
    User user=userRepository.findByUserId(userName).orElseThrow(()->new IllegalArgumentException("유저를 찾을 수 없음"));

    String visitCode= UUID.randomUUID().toString();
    AccessHistory accessHistory=AccessHistory.createAccessHistoryBuilder(
        request.isOwner(),
        request.visitPurpose(),
        visitCode,
        user
    );
    accessHistoryRepository.save(accessHistory);
    return visitCode;
  }
}
