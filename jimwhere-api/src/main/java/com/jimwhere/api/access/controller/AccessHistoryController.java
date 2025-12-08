package com.jimwhere.api.access.controller;


import com.jimwhere.api.access.dto.request.CreateAccessHistoryRequest;
import com.jimwhere.api.access.service.AccessHistoryService;
import com.jimwhere.api.global.config.security.CustomUser;
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
public class AccessHistoryController {
  private final AccessHistoryService accessHistoryService;
  @PostMapping("/user/access/history")
  public ResponseEntity<String> createAccessHistory(@RequestBody CreateAccessHistoryRequest request
  ,@AuthenticationPrincipal CustomUser user) {
    String userName=user.getUsername();
    return ResponseEntity.ok().body(accessHistoryService.createAccessHistory(request,userName));
  }

}
