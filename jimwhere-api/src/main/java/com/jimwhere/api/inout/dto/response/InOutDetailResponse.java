package com.jimwhere.api.inout.dto.response;

import com.jimwhere.api.inout.domain.InOutType;
import lombok.Getter;

@Getter
public class InOutDetailResponse {
  private Long inOutHistoryCode;
  private String inOutType;
  private String inOutName;
  private Long inOutQuantity;
  private String boxName;

  public InOutDetailResponse(Long inOutHistoryCode, InOutType inOutType, String inOutName, Long inOutQuantity, String boxName) {
    this.inOutHistoryCode = inOutHistoryCode;
    this.inOutType = inOutType.name();
    this.inOutName = inOutName;
    this.inOutQuantity = inOutQuantity;
    this.boxName = boxName;
  }
}