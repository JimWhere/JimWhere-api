package com.jimwhere.api.auth.service;

import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.dto.reqeust.UserCreateRequest;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void createUser(UserCreateRequest request) {

        boolean existUserId = userRepository.existsByUserId(request.getUserId());
        boolean existPhoneNumber = userRepository.existsByUserPhoneNumber(request.getUserPhoneNumber());
        boolean existBusinessNumber = userRepository.existsByUserBusinessNumber(request.getUserBusinessNumber());

        if (existUserId || existPhoneNumber || existBusinessNumber) {
            throw new IllegalArgumentException("이미 가입 된 정보입니다");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.createUser(
                request.getUserId(),
                encodedPassword,
                request.getUserPhoneNumber(),
                request.getUserBusinessNumber()
        );

        userRepository.save(user);
    }
}
