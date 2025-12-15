package com.jimwhere.api.room.controller;

import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.room.dto.RoomDto;
import com.jimwhere.api.room.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name="방 API")
@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

  @Operation(
      summary = "방 생성", description = "관리자가 방을 생성하는 api"
  )
    @PostMapping
    public ResponseEntity<ApiResponse<RoomDto.Response>> createRoom(@RequestBody @Valid RoomDto.CreateRequest request) {
        RoomDto.Response dto = roomService.createRoom(request);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

  @Operation(
      summary = "방 수정", description = "관리자가 특정 방의 정보를 수정하는 api "
  )
    @PutMapping("/{roomCode}")
    public ResponseEntity<ApiResponse<RoomDto.Response>> updateRoom(@PathVariable Long roomCode, @RequestBody @Valid RoomDto.UpdateRequest request) {
        RoomDto.Response dto = roomService.updateRoom(roomCode, request);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

  @Operation(
      summary = "방 리스트 조회", description = "관리자와 유저가 방의 리스트를 조회하는 api"
  )
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomDto.Response>>> listRooms() {
        List<RoomDto.Response> list = roomService.listRooms();
        return ResponseEntity.ok(ApiResponse.success(list));
    }
  @Operation(
      summary = "방 상세 조회", description = "관리자와 유저가 방의 상세 정보를 조회하는 api"
  )
    @GetMapping("/{roomCode}")
    public ResponseEntity<ApiResponse<RoomDto.Response>> getRoom(@PathVariable Long roomCode) {
        RoomDto.Response dto = roomService.getRoom(roomCode);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

  @Operation(
      summary = "방 삭제", description = "관리자가 방을 삭제하는 api"
  )
    @DeleteMapping("/{roomCode}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable Long roomCode) {
        roomService.deleteRoom(roomCode);
        return ResponseEntity.ok(ApiResponse.success(null));
    }


    // 방 코드 (타입별) 뽑아오기
    @Operation(
        summary = "방 코드 (대시보드용)", description = "관리자 대시보드에 표기될 방 코드를 조회하는 api"
    )
    @GetMapping("/rooms/stat")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRoomStats() {
        Map<String, Object> stats = roomService.getRoomStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

}
