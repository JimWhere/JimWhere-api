package com.jimwhere.api.inout.dto.response;

import com.jimwhere.api.inout.domain.InOutHistory;
import com.jimwhere.api.inout.domain.InOutType;

public record InOutHistoryResponse (
    Long inOutHistoryCode,
    InOutType inOutType,
    String inOutName,
    Long inOutQuantity,
    Long roomCode,
    String boxName


  ) {
  public static InOutHistoryResponse from(InOutHistory inoutHistory) {
    return new InOutHistoryResponse(
        inoutHistory.getInOutHistoryCode(),
        inoutHistory.getInOutType(),
        inoutHistory.getInOutName(),
        inoutHistory.getInOutQuantity(),
        inoutHistory.getAccessHistory().getRoomCode(),
        inoutHistory.getBox().getBoxName()
    );
  }
}
