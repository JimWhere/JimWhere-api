package com.jimwhere.api.box.controller;

import com.jimwhere.api.box.dto.BoxDto;
import com.jimwhere.api.box.service.BoxService;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoxController {

    private final BoxService boxService;

    // 방의 박스 목록 조회
    @GetMapping("/room/{roomCode}/boxes")
    public ResponseEntity<ApiResponse<List<BoxDto.Response>>> listBoxesByRoom(@PathVariable Long roomCode) {
        List<BoxDto.Response> list = boxService.listBoxesByRoom(roomCode);
        return ResponseEntity.ok(ApiResponse.success(list));
    }
    
    // 방의 이용가능한 박스개수 조회
    @GetMapping("/room/{roomCode}/boxes/count")
    public ResponseEntity<ApiResponse<Long>> countAvailableBoxes(
            @PathVariable Long roomCode,
            @RequestParam(name = "status", defaultValue = "Y") String status) {
        
        long count = boxService.countAvailableBoxes(roomCode, status);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    // 특정 방의 전체 재고 합계
    @GetMapping("/room/{roomCode}/boxes/total")
    public ResponseEntity<ApiResponse<Long>> getTotalBoxCurrentCount(@PathVariable Long roomCode) {
        long total = boxService.getTotalBoxCurrentCount(roomCode);
        return ResponseEntity.ok(ApiResponse.success(total));
    }
  @GetMapping("/user/room/boxes")
  public ResponseEntity<ApiResponse<List<BoxDto.Response>>> listBoxesByRoom(
      @AuthenticationPrincipal CustomUser user) {
      String userName= user.getUsername();
    List<BoxDto.Response> list = boxService.listBoxesByRoom(userName);
    return ResponseEntity.ok(ApiResponse.success(list));
  }
  @GetMapping("/admin/room/boxes")
  public ResponseEntity<ApiResponse<List<BoxDto.Response>>> listBoxesByRoom() {
    List<BoxDto.Response> list = boxService.listBoxesByRoomAll();
    return ResponseEntity.ok(ApiResponse.success(list));
  }

  // 방의 이용가능한 박스개수 조회
  @GetMapping("/user/boxes/count")
  public ResponseEntity<ApiResponse<Long>> countAvailableBoxes(
      @AuthenticationPrincipal CustomUser user,
      @RequestParam(name = "status", defaultValue = "Y") String status) {
    String userName= user.getUsername();
    long count = boxService.countAvailableBoxes(userName, status);
    return ResponseEntity.ok(ApiResponse.success(count));
  }
/*
  // 특정 방의 전체 재고 합계
  @GetMapping("/room/{roomCode}/boxes/total")
  public ResponseEntity<ApiResponse<Long>> getTotalBoxCurrentCount(@PathVariable Long roomCode) {
    long total = boxService.getTotalBoxCurrentCount(roomCode);
    return ResponseEntity.ok(ApiResponse.success(total));
  }*/
}