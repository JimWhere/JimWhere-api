package com.jimwhere.api.room.service;

import com.jimwhere.api.box.repository.BoxRepository;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.room.domain.Room;
import com.jimwhere.api.room.dto.RoomDto;
import com.jimwhere.api.room.repository.RoomRepository;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final BoxRepository boxRepository;
    private final UserRepository userRepository;

    @Override
    public RoomDto.Response createRoom(RoomDto.CreateRequest request) {
        
        // 요청 체크
        if (request == null) throw new CustomException(ErrorCode.INVALID_REQUEST);
        
        // 입력 검증 및 정규화
        String roomName = request.getRoomName();
        if (roomName == null || roomName.isBlank()) throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT);
        roomName = roomName.trim();

        // 중복 체크 1 : 비즈니스 로직단의 확인
        if (roomRepository.existsByRoomName(roomName)) {
            throw new CustomException(ErrorCode.ROOM_ALREADY_EXISTS);
        }
        
        Room room = new Room();
        room.setRoomName(roomName);
        room.setRoomWidth(request.getRoomWidth());
        room.setRoomLength(request.getRoomLength());
        room.setRoomHeight(request.getRoomHeight());

        try {
            Room saved = roomRepository.save(room);
            return toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            // 중복 체크 2 : DB 제약단에서의 확인
            throw new CustomException(ErrorCode.ROOM_ALREADY_EXISTS);
        }
    }

    @Override
    public RoomDto.Response updateRoom(Long roomCode, RoomDto.UpdateRequest request) {
        // 조회 체크 : 비즈니스 로직단
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
        // 1) 방 존재 확인
        Room room = roomRepository.findById(roomCode)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        // 2) 해당 방에 박스가 있는지 확인 (BoxRepository 필요)
        boolean boxExists = boxRepository.existsByRoomRoomCode(roomCode);
        if (boxExists) {
            throw new CustomException(ErrorCode.ROOM_NOT_EMPTY);
        }
        
        // 3) 삭제 수행
        roomRepository.delete(room);
    }

    private RoomDto.Response toResponse(Room r) {
        String userName = null;

        if (r.getUserCode() != null) {
            userName = userRepository.findById(r.getUserCode())
                    .map(User::getPName) // 네 User 필드명에 맞게
                    .orElse(null);
        }

        return RoomDto.Response.builder()
                .roomCode(r.getRoomCode())
                .roomName(r.getRoomName())
                .roomWidth(r.getRoomWidth())
                .roomLength(r.getRoomLength())
                .roomHeight(r.getRoomHeight())
                .userCode(r.getUserCode())
                .userName(userName)
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }

    @Override
    public Map<String, Object> getRoomStats() {
        List<Room> rooms = roomRepository.findAll();

        int total = rooms.size();
        int using = 0;

        int usingA = 0, usingB = 0, usingC = 0;
        int totalA = 5, totalB = 5, totalC = 5;

        for (Room r : rooms) {
            boolean isUsing = r.getUserCode() != null;
            if (isUsing) using++;

            Long code = r.getRoomCode();

            if (code >= 1 && code <= 5) {
                if (isUsing) usingA++;
            } else if (code >= 6 && code <= 10) {
                if (isUsing) usingB++;
            } else if (code >= 11 && code <= 15) {
                if (isUsing) usingC++;
            }
        }

        int empty = total - using;

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("using", using);
        result.put("empty", empty);

        Map<String, Object> types = new HashMap<>();

        types.put("A", Map.of(
                "total", totalA,
                "using", usingA,
                "empty", totalA - usingA
        ));
        types.put("B", Map.of(
                "total", totalB,
                "using", usingB,
                "empty", totalB - usingB
        ));
        types.put("C", Map.of(
                "total", totalC,
                "using", usingC,
                "empty", totalC - usingC
        ));

        result.put("types", types);

        return result;
    }

}
