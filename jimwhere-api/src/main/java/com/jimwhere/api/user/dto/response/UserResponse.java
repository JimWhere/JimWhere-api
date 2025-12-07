package com.jimwhere.api.user.dto.response;

import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.domain.UserRole;
import com.jimwhere.api.user.domain.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private final long userCode;
    private final String userId;
    private final String userPhonNumber;
    private final String userBusinessNumber;
    private final String pName;
    private final String startDt;
    private final UserRole role;
    private final UserStatus status;


    public static UserResponse from(User u) {

        return new UserResponse(
                u.getUserCode(),u.getUserId(),
                u.getUserPhoneNumber(),u.getUserBusinessNumber(),
                u.getPName(),u.getStartDt(),
                u.getRole(),u.getStatus()
        );
    }

}
