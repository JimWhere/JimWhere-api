package com.jimwhere.api.box.controller;

import com.jimwhere.api.box.dto.BoxDto;
import com.jimwhere.api.box.service.BoxService;
import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.global.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="박스 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoxController {

    private final BoxService boxService;

    // 방의 박스 목록 조회
    @Operation(
        summary = "방의 박스 리스트 조회", description = "특정 방의 박스리스트를 전체 조회하는 api"
    )
    @GetMapping("/room/{roomCode}/boxes")
    public ResponseEntity<ApiResponse<List<BoxDto.Response>>> listBoxesByRoom(@PathVariable Long roomCode) {
        List<BoxDto.Response> list = boxService.listBoxesByRoom(roomCode);
        return ResponseEntity.ok(ApiResponse.success(list));
    }
    
    // 방의 이용가능한 박스개수 조회
    @Operation(
        summary = "방의 이용가능한 박스 갯수 조회", description = "특정 방의 이용가능한 박스 갯수를 조회하는 api"
    )
    @GetMapping("/room/{roomCode}/boxes/count")
    public ResponseEntity<ApiResponse<Long>> countAvailableBoxes(
            @PathVariable Long roomCode,
            @RequestParam(name = "status", defaultValue = "Y") String status) {
        
        long count = boxService.countAvailableBoxes(roomCode, status);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    // 특정 방의 전체 재고 합계
    @Operation(
        summary = "특정 방의 전체 재고 합계", description = "특정 방의 전체 재고의 합계를 조회하는 api"
    )
    @GetMapping("/room/{roomCode}/boxes/total")
    public ResponseEntity<ApiResponse<Long>> getTotalBoxCurrentCount(@PathVariable Long roomCode) {
        long total = boxService.getTotalBoxCurrentCount(roomCode);
        return ResponseEntity.ok(ApiResponse.success(total));
    }
  @Operation(
      summary = "유저의 방 박스 조회", description = "유저가 대여한 방에 존재하는 박스의 재고를 조회하는 api"
  )
  @GetMapping("/user/room/boxes")
  public ResponseEntity<ApiResponse<List<BoxDto.Response>>> listBoxesByRoom(
      @AuthenticationPrincipal CustomUser user) {
      String userName= user.getUsername();
    List<BoxDto.Response> list = boxService.listBoxesByRoom(userName);
    return ResponseEntity.ok(ApiResponse.success(list));
  }
  @Operation(
      summary = "관리자 박스 전체 리스트 조회 ", description = "관리자가 전체 박스의 재고를 조회하는 api"
  )
  @GetMapping("/admin/room/boxes")
  public ResponseEntity<ApiResponse<PageResponse<BoxDto.Response>>> listBoxesByRoom(
      @RequestParam(required = false) Long roomCode,
      @PageableDefault Pageable pageable
  ) {
    Page<BoxDto.Response> list = boxService.listBoxesByRoomAll(pageable,roomCode);
    return ResponseEntity.ok(ApiResponse.success(PageResponse.of(list)));
  }

  // 유저 이용가능한 박스개수 조회
  @Operation(
      summary = "유저 비어있는 박스 갯수 조회", description = "유저가 대여한 방에 비어있는 박스의 갯수를 조회하는 api"
  )
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