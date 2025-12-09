package com.jimwhere.api.access.dto.request;

import com.jimwhere.api.access.domain.IsOwner;
import com.jimwhere.api.access.domain.VisitPurpose;
import com.jimwhere.api.inout.dto.request.CreateInOutHistoryRequest;
import java.util.List;
import lombok.Getter;

@Getter
public class CreateAccessHistoryRequest {
  private IsOwner isOwner;
  private VisitPurpose visitPurpose;
  private Long roomCode;
  private List<CreateInOutHistoryRequest> inOutHistoryRequestList;
}