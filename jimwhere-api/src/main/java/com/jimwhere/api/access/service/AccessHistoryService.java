package com.jimwhere.api.access.service;

import com.jimwhere.api.access.dto.request.CreateAccessHistoryRequest;
import com.jimwhere.api.access.dto.response.AccessHistoryDetailResponse;
import com.jimwhere.api.access.dto.response.AccessHistoryResponse;
import com.jimwhere.api.user.dto.response.QrIssueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccessHistoryService {
  QrIssueResponse createAccessHistory(CreateAccessHistoryRequest request, String userName);
  QrIssueResponse updateAccessHistory(CreateAccessHistoryRequest request, String userName,Long accessHistoryCode);
  Page<AccessHistoryResponse> getAccessHistoryList(Pageable pageable,String userName);
  Page<AccessHistoryResponse> getAccessHistoryListAll(Pageable pageable);
  AccessHistoryDetailResponse selectDetailByCode(Long code);

}
