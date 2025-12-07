package com.jimwhere.api.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {
    private final String userId;
    private final String password;
    private final String userPhoneNumber;
    private final String userBusinessNumber;
    private final String pName;
    private final String startDt;
}
