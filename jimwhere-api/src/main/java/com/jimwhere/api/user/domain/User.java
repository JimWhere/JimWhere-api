package com.jimwhere.api.user.domain;

import com.jimwhere.api.global.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;

    @Column(nullable = false, unique = true)
    private String userId;  // 로그인 ID

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userPhoneNumber;

    // DB에는 VARCHAR("USER", "ADMIN") 로 저장됨
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false)
    private String userBusinessNumber;

    @Column(name = "p_name", nullable = false)
    private String pName; // 사업자대표명

    @Column(name = "start_dt", nullable = false)
    private String startDt; // 개업일자


    // 기본값 세팅을 위한 유저생성 메서드
    public static User createUser(
            String userId, String password, String userPhoneNumber, String userBusinessNumber
    ,String pName, String startDt){

        return User.builder()
                .userId(userId)
                .password(password)
                .userPhoneNumber(userPhoneNumber)
                .userBusinessNumber(userBusinessNumber)
                .pName(pName)
                .startDt(startDt)
                .role(UserRole.USER)
                .status(UserStatus.Y)
                .build();
    }



    public void updatePhone(String newPhoneNumber) {
        this.userPhoneNumber = newPhoneNumber;
    }

    public void modifyUserStatus(UserStatus status) {
        this.status = status;
    }
    public void modifyUserRole(UserRole role) {
        this.role = role;
    }

}
