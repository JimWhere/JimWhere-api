package com.jimwhere.api.box.service;

import java.util.List;

import com.jimwhere.api.box.dto.BoxDto;

public interface BoxService {
    List<BoxDto.Response> listBoxesByRoom(Long roomCode);
    List<BoxDto.Response> listBoxesByRoom(String userName);
    List<BoxDto.Response> listBoxesByRoomAll();

    long countAvailableBoxes(Long roomCode, String boxPossibleStatus);
    long countAvailableBoxes(String userName, String boxPossibleStatus);
    long getTotalBoxCurrentCount(Long roomCode);
}
