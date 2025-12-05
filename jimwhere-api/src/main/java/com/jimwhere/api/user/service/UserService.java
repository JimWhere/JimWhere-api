package com.jimwhere.api.user.service;

import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.domain.UserRole;
import com.jimwhere.api.user.domain.UserStatus;
import com.jimwhere.api.user.dto.reqeust.UserCreateRequest;
import com.jimwhere.api.user.dto.reqeust.UserUpdatePhoneRequest;
import com.jimwhere.api.user.dto.reqeust.UserUpdateRequest;
import com.jimwhere.api.user.dto.response.UserResponse;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

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


    public List<UserResponse> findAll() {

        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }


    public UserResponse findMyInfo(String username) {

        User foundUser = userRepository.findByUserId(username).orElseThrow(() ->
                new IllegalArgumentException("가입정보가 없습니다"));

        return UserResponse.from(foundUser);
    }

    public UserResponse findUserByCode(long userCode) {
        User foundUser = userRepository.findById(userCode).orElseThrow(() ->
                new IllegalArgumentException("가입정보가 없습니다"));

        return UserResponse.from(foundUser);
    }

    @Transactional
    public void updateUserPhoneNumber(String username, UserUpdatePhoneRequest request) {
        User foundUser = userRepository.findByUserId(username).orElseThrow(() ->
                new IllegalArgumentException("가입정보가 없습니다"));
        boolean foundPhoneNumber = userRepository.existsByUserPhoneNumber(request.getNewPhoneNumber());

        if (foundUser.getUserPhoneNumber().equals(request.getNewPhoneNumber())) {
            throw new IllegalArgumentException("전화번호가 동일합니다");
        }
        if (foundPhoneNumber) {
            throw new IllegalArgumentException("이미 존재하는 번호입니다.");
        }

        foundUser.updatePhone(request.getNewPhoneNumber());
    }

    @Transactional
    public void deactivateUser(String username) {
        User foundUser = userRepository.findByUserId(username).orElseThrow(() ->
                new IllegalArgumentException("가입정보가 없습니다"));

        foundUser.modifyUserStatus(UserStatus.N);
    }

    @Transactional
    public void updateUserAdminSettings(long userCode, UserUpdateRequest request) {
        String rawStatus = request.getStatus();
        String rawRole = request.getRole();

        if ("".equals(rawStatus) || "".equals(rawRole)) {
            throw new IllegalArgumentException("빈 값은 허용되지 않습니다.");
        }

        if (rawStatus == null && rawRole == null) {
            throw new IllegalArgumentException("변경할 값이 하나 이상 필요합니다.");
        }

        User foundUser = userRepository.findById(userCode)
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다"));


        if (rawStatus != null) {
            UserStatus statusEnum = UserStatus.valueOf(rawStatus);
            foundUser.modifyUserStatus(statusEnum);
        }

        if (rawRole != null) {
            UserRole roleEnum = UserRole.valueOf(rawRole);
            foundUser.modifyUserRole(roleEnum);
        }

    }
}
