package com.jimwhere.api.user.dto.reqeust;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {
    private String userId;
    private String password;
    private String userPhoneNumber;
    private String userBusinessNumber;
}
