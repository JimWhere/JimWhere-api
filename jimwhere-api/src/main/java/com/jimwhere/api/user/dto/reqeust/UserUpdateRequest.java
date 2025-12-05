package com.jimwhere.api.user.dto.reqeust;

import com.jimwhere.api.user.domain.UserRole;
import com.jimwhere.api.user.domain.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String status;
    private String role;
}
