package com.jimwhere.api.user.domain;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;

    @Column(nullable = false, unique = true)
    private String userId;  // 로그인 ID

    @Column(nullable = false)
    private String password;

    // DB에는 VARCHAR("USER", "ADMIN") 로 저장됨
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

}
