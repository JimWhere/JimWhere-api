package com.jimwhere.api.access.dto.response;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.access.domain.IsOwner;
import com.jimwhere.api.access.domain.VisitPurpose;

public record AccessHistoryResponse (
    Long accessHistoryCode,
    IsOwner isOwner,
  VisitPurpose visitPurpose,
  Long roomCode

){

  public static AccessHistoryResponse  from(AccessHistory accessHistory) {
    return new AccessHistoryResponse(
        accessHistory.getAccessHistoryCode(),
    accessHistory.getIsOwner(),
    accessHistory.getVisitPurpose(),
        accessHistory.getRoomCode());
  }
}

