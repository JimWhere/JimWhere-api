package com.jimwhere.api.user.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePhoneRequest {

    private String newPhoneNumber;

}
