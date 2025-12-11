package com.jimwhere.api.inout.dto.response;

import com.jimwhere.api.inout.domain.InOutHistory;
import com.jimwhere.api.inout.domain.InOutType;

public record InOutHistoryAllResponse (
    Long inOutHistoryCode,
    InOutType inOutType,
    String inOutName,
    Long inOutQuantity,
    Long roomCode,
    String userId

) {
  public static InOutHistoryAllResponse from(InOutHistory inoutHistory) {
    return new InOutHistoryAllResponse(
        inoutHistory.getInOutHistoryCode(),
        inoutHistory.getInOutType(),
        inoutHistory.getInOutName(),
        inoutHistory.getInOutQuantity(),
        inoutHistory.getAccessHistory().getRoomCode(),
        inoutHistory.getAccessHistory().getUser().getUserId()
    );
  }
}
