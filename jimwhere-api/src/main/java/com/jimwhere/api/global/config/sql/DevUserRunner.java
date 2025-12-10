package com.jimwhere.api.global.config.sql;

import com.jimwhere.api.auth.dto.UserCreateRequest;
import com.jimwhere.api.auth.service.UserAuthService;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.domain.UserRole;
import com.jimwhere.api.user.domain.UserStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

//@Profile("dev")
@Component
public class DevUserRunner implements CommandLineRunner {

    private final UserAuthService service;

    public DevUserRunner(UserAuthService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        UserCreateRequest adminRequest = new UserCreateRequest(
                "admin1",
                "1234",
                "01012341234",
                "111111111111",
                "관리자1",
                "2025-12-12"
        );

        service.createAdmin(adminRequest);

        for (int i = 1; i <= 10; i++) {

            String userId = "user" + i;
            String password = "1234";
            String phone = "0100000000" + i;
            String businessNumber = "123456789" + i;
            String pName = "대표자명" + i;
            String startDate = "2025-01-11";

            UserCreateRequest req = new UserCreateRequest(
                    userId,
                    password,
                    phone,
                    businessNumber,
                    pName,
                    startDate
            );

            service.createUser(req);

        }

        System.out.println("어드민1 , 유저10개 더미데이터 생성");
    }
}