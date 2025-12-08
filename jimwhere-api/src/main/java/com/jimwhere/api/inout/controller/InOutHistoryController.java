package com.jimwhere.api.inout.controller;

import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.inout.dto.request.CreateInOutHistoryRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InOutHistoryController {
  @PostMapping("/user/in/out/history")
  public ResponseEntity<String> createInOutHistory(@RequestBody CreateInOutHistoryRequest request,
      @AuthenticationPrincipal CustomUser user) {
    String userName=user.getUsername();
    return null;

  }

}
