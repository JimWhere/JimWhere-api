package com.jimwhere.api.access.dto.response;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.access.domain.AccessResult;
import com.jimwhere.api.access.domain.IsOwner;
import com.jimwhere.api.access.domain.VisitPurpose;
import java.time.LocalDateTime;

public record AccessHistoryAllResponse (
    Long accessHistoryCode,
    IsOwner isOwner,
    VisitPurpose visitPurpose,
    Long roomCode,
    LocalDateTime accessedAt,
    AccessResult accessResult,
    String userId
) {

  public static AccessHistoryAllResponse from(AccessHistory accessHistory) {
    return new AccessHistoryAllResponse(
        accessHistory.getAccessHistoryCode(),
        accessHistory.getIsOwner(),
        accessHistory.getVisitPurpose(),
        accessHistory.getRoomCode(),
        accessHistory.getAccessedAt(),
        accessHistory.getAccessResult(),
        accessHistory.getUser().getUserId()
    );
  }
}