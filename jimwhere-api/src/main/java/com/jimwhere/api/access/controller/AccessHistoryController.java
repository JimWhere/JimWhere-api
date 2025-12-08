package com.jimwhere.api.access.controller;


import com.jimwhere.api.access.dto.request.CreateAccessHistoryRequest;
import com.jimwhere.api.access.service.AccessHistoryService;
import com.jimwhere.api.global.config.security.CustomUser;
import com.jimwhere.api.user.dto.response.QrIssueResponse;
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
    public ResponseEntity<QrIssueResponse> createAccessHistory(
            @RequestBody CreateAccessHistoryRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        String userName = user.getUsername();
        QrIssueResponse response = accessHistoryService.createAccessHistory(request, userName);
        return ResponseEntity.ok(response);
    }


}


