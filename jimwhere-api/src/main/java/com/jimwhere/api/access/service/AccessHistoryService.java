package com.jimwhere.api.access.service;

import com.jimwhere.api.access.dto.request.CreateAccessHistoryRequest;

public interface AccessHistoryService {
  String createAccessHistory(CreateAccessHistoryRequest request,String userName);

}
