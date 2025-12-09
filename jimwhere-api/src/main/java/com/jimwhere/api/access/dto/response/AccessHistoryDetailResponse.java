package com.jimwhere.api.access.dto.response;


import com.jimwhere.api.inout.dto.response.InOutDetailResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessHistoryDetailResponse {

  private Long accessHistoryCode;
  private String isOwner;
  private String visitPurpose;
  private String visitCode;
  private LocalDateTime accessedAt;
  private String accessResult;
  private String userName;

  private List<InOutDetailResponse> inOutList;

}
