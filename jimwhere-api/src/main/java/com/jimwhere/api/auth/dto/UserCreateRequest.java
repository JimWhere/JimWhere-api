package com.jimwhere.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Time;

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
