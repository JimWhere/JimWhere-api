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

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "개업일자는 yyyy-MM-dd 형식이어야 합니다.")
    private final String startDt;
}
