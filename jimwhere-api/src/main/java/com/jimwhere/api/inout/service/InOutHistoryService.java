package com.jimwhere.api.inout.service;

import com.jimwhere.api.inout.domain.InOutType;
import com.jimwhere.api.inout.dto.request.UpdateInOutHistoryRequest;
import com.jimwhere.api.inout.dto.response.InOutHistoryAllResponse;
import com.jimwhere.api.inout.dto.response.InOutHistoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InOutHistoryService {


  String updateInOutHistory(Long inOutHistoryCode, UpdateInOutHistoryRequest request);


  Page<InOutHistoryResponse>findInOutHistoryList(      Long roomCode,
      String boxName,
      String userName,
      InOutType inOutType,
      String inOutName,
      Long inOutHistoryCode,Pageable pageable);

  Page<InOutHistoryAllResponse> findInOutHistoryListAll(Long roomCode, String boxName, String userId, Pageable pageable);
}
