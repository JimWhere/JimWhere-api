package com.jimwhere.api.room.controller;

import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.room.dto.RoomDto;
import com.jimwhere.api.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoomDto.Response>> createRoom(@RequestBody @Valid RoomDto.CreateRequest request) {
        RoomDto.Response dto = roomService.createRoom(request);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PutMapping("/{roomCode}")
    public ResponseEntity<ApiResponse<RoomDto.Response>> updateRoom(@PathVariable Long roomCode, @RequestBody @Valid RoomDto.UpdateRequest request) {
        RoomDto.Response dto = roomService.updateRoom(roomCode, request);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomDto.Response>>> listRooms() {
        List<RoomDto.Response> list = roomService.listRooms();
        return ResponseEntity.ok(ApiResponse.success(list));
    }
    
    @GetMapping("/{roomCode}")
    public ResponseEntity<ApiResponse<RoomDto.Response>> getRoom(@PathVariable Long roomCode) {
        RoomDto.Response dto = roomService.getRoom(roomCode);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @DeleteMapping("/{roomCode}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable Long roomCode) {
        roomService.deleteRoom(roomCode);
        return ResponseEntity.ok(ApiResponse.success(null));
    }


    // 방 코드 (타입별) 뽑아오기
    @GetMapping("/rooms/stat")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRoomStats() {
        Map<String, Object> stats = roomService.getRoomStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

}
