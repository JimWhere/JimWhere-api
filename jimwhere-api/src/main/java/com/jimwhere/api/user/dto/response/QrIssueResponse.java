package com.jimwhere.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QrIssueResponse {
    private String qrToken;
    private byte[] qrImage;
}