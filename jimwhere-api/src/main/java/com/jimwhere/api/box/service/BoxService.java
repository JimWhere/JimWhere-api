package com.jimwhere.api.box.service;

import java.util.List;

import com.jimwhere.api.box.dto.BoxDto;

public interface BoxService {
    List<BoxDto.Response> listBoxesByRoom(Long roomCode);
    
    long countAvailableBoxes(Long roomCode, String boxPossibleStatus);
    long getTotalBoxCurrentCount(Long roomCode);
}
