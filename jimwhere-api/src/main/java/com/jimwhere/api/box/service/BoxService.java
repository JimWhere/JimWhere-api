package com.jimwhere.api.box.service;


import java.util.List;

import com.jimwhere.api.box.dto.BoxDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoxService {
    List<BoxDto.Response> listBoxesByRoom(Long roomCode);
    List<BoxDto.Response> listBoxesByRoom(String userName);
  Page<BoxDto.Response>  listBoxesByRoomAll(Pageable pageable,Long roomCode);

    long countAvailableBoxes(Long roomCode, String boxPossibleStatus);
    long countAvailableBoxes(String userName, String boxPossibleStatus);
    long getTotalBoxCurrentCount(Long roomCode);
}
