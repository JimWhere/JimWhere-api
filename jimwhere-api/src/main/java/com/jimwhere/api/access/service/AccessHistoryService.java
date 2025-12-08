package com.jimwhere.api.access.service;

import com.jimwhere.api.access.dto.request.CreateAccessHistoryRequest;
import com.jimwhere.api.user.dto.response.QrIssueResponse;

public interface AccessHistoryService {
  QrIssueResponse createAccessHistory(CreateAccessHistoryRequest request, String userName);

}
