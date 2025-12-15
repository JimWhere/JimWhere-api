package com.jimwhere.api.inout.controller;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.config.security.CustomUser;

import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.inout.domain.InOutType;
import com.jimwhere.api.inout.dto.request.UpdateInOutHistoryRequest;
import com.jimwhere.api.inout.dto.response.InOutHistoryAllResponse;
import com.jimwhere.api.inout.dto.response.InOutHistoryResponse;
import com.jimwhere.api.inout.service.InOutHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Tag(name="입출고 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InOutHistoryController {

  private final InOutHistoryService inOutHistoryService;
  //유저 입출고 내역 수정
  @Operation(
      summary = "유저 입출고 내역 수정", description = "유저가 입출고 내역 정보를 수정하는 api "
  )
  @PutMapping("/user/inout/history/{inOutHistoryCode}")
  public ResponseEntity<ApiResponse<String>> updateInOutHistory(
      @PathVariable Long inOutHistoryCode,
      @RequestBody UpdateInOutHistoryRequest request,
      @AuthenticationPrincipal CustomUser user
  ) {
    return
        ResponseEntity.ok(ApiResponse.success(
            inOutHistoryService.updateInOutHistory(inOutHistoryCode, request)));
  }
  //유저 입출고 내역 조회
  @Operation(
      summary = "유저 입출고 내역 조회", description = "유저가 입출고 내역을 조회하는 api"
  )
  @GetMapping("/user/inout/history")
  public ApiResponse<PageResponse<InOutHistoryResponse>> findInOutHistoryList(
      @AuthenticationPrincipal CustomUser user,
      @RequestParam(required = false) Long roomCode,
      @RequestParam(required = false) String boxName,
      @RequestParam(required = false) String inOutName,
      @RequestParam(required = false) Long inOutHistoryCode,
      @RequestParam(required = false) InOutType inOutType,
      @PageableDefault Pageable pageable
  ) {
    String userName = user.getUsername();
    Page<InOutHistoryResponse> inoutHistoryList = inOutHistoryService.findInOutHistoryList(    roomCode,
         boxName,
        userName,
         inOutType,
        inOutName,
         inOutHistoryCode, pageable);
    return ApiResponse.success(PageResponse.of(inoutHistoryList));
  }
//관리자 입출고 내역 조회
@Operation(
    summary = "관리자 입출고 내역 조회", description = "관리자가 전체 유저의 입출고 내역을 조회하는 api"
)
  @GetMapping("/admin/inout/history")
  public ApiResponse<PageResponse<InOutHistoryAllResponse>> findInOutHistoryListAll(
      @RequestParam(required = false) Long roomCode,
      @RequestParam(required = false) String boxName,
      @RequestParam(required = false) String userId,
      @PageableDefault Pageable pageable
  ) {
    Page<InOutHistoryAllResponse> inoutHistoryList = inOutHistoryService.findInOutHistoryListAll(roomCode,
        boxName,userId, pageable);
    return ApiResponse.success(PageResponse.of(inoutHistoryList));
  }
}
