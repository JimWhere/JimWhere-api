package com.jimwhere.api.inout.dto.request;

import com.jimwhere.api.inout.domain.InOutType;
import lombok.Getter;

@Getter
public class UpdateInOutHistoryRequest {
  private InOutType inOutType;
  private String inOutName;
  private Long inOutQuantity;
}
