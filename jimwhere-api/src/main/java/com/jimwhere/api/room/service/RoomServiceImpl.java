package com.jimwhere.api.room.service;

import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.room.domain.Room;
import com.jimwhere.api.room.dto.RoomDto;
import com.jimwhere.api.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public RoomDto.Response createRoom(RoomDto.CreateRequest request) {
        if (request == null) throw new CustomException(ErrorCode.INVALID_REQUEST);

        Room room = new Room();
        room.setRoomName(request.getRoomName());
        room.setRoomWidth(request.getRoomWidth());
        room.setRoomLength(request.getRoomLength());
        room.setRoomHeight(request.getRoomHeight());

        Room saved = roomRepository.save(room);

        return toResponse(saved);
    }

    @Override
    public RoomDto.Response updateRoom(Long roomCode, RoomDto.UpdateRequest request) {
        Room room = roomRepository.findById(roomCode)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        if (request.getRoomName() != null) room.setRoomName(request.getRoomName());
        if (request.getRoomWidth() != null) room.setRoomWidth(request.getRoomWidth());
        if (request.getRoomLength() != null) room.setRoomLength(request.getRoomLength());
        if (request.getRoomHeight() != null) room.setRoomHeight(request.getRoomHeight());

        Room updated = roomRepository.save(room);
        return toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto.Response> listRooms() {
        return roomRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public RoomDto.Response getRoom(Long roomCode) {
        Room room = roomRepository.findById(roomCode)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        return toResponse(room);
    }

    @Override
    public void deleteRoom(Long roomCode) {
        Room room = roomRepository.findById(roomCode)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        roomRepository.delete(room);
    }

    private RoomDto.Response toResponse(Room r) {
        return RoomDto.Response.builder()
                .roomCode(r.getRoomCode())
                .roomName(r.getRoomName())
                .roomWidth(r.getRoomWidth())
                .roomLength(r.getRoomLength())
                .roomHeight(r.getRoomHeight())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }
}
