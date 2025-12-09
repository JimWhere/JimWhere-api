package com.jimwhere.api.auth.service;

import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.auth.dto.UserCreateRequest;
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


        String normalizedPhone = normalizeNumber(request.getUserPhoneNumber());
        String normalizedBusiness = normalizeNumber(request.getUserBusinessNumber());

        boolean existUserId = userRepository.existsByUserId(request.getUserId());
        boolean existPhoneNumber = userRepository.existsByUserPhoneNumber(normalizedPhone);
        boolean existBusinessNumber = userRepository.existsByUserBusinessNumber(normalizedBusiness);

        if (existUserId || existPhoneNumber || existBusinessNumber) {
            throw new CustomException(ErrorCode.INVALID_INCORRECT_FORMAT,"이미 가입 된 정보입니다");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.createUser(
                request.getUserId(),
                encodedPassword,
                normalizedPhone,
                normalizedBusiness,
                request.getPName(),
                request.getStartDt()
        );

        userRepository.save(user);
    }


    private String normalizeNumber(String value) {
        if (value == null) return null;
        return value.replaceAll("-", "");
    }
}
