package com.jimwhere.api.room.service;

import com.jimwhere.api.room.dto.RoomDto;
import java.util.List;

public interface RoomService {
    RoomDto.Response createRoom(RoomDto.CreateRequest request);
    RoomDto.Response updateRoom(Long roomCode, RoomDto.UpdateRequest request);
    RoomDto.Response getRoom(Long roomCode);
    List<RoomDto.Response> listRooms();
    void deleteRoom(Long roomCode);
}
