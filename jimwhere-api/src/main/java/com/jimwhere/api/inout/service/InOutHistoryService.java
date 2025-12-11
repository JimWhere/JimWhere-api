package com.jimwhere.api.inout.service;

import com.jimwhere.api.inout.dto.request.UpdateInOutHistoryRequest;
import com.jimwhere.api.inout.dto.response.InOutHistoryAllResponse;
import com.jimwhere.api.inout.dto.response.InOutHistoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InOutHistoryService {


  String updateInOutHistory(Long inOutHistoryCode, UpdateInOutHistoryRequest request);
  Page<InOutHistoryResponse> findInOutHistoryList(Pageable pageable, String user);
  Page<InOutHistoryAllResponse> findInOutHistoryListAll(Pageable pageable);
}
