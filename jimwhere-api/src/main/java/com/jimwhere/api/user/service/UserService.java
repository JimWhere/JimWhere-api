package com.jimwhere.api.user.service;

import com.jimwhere.api.global.comman.PageResponse;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.domain.UserRole;
import com.jimwhere.api.user.domain.UserStatus;
import com.jimwhere.api.user.dto.reqeust.UserUpdatePhoneRequest;
import com.jimwhere.api.user.dto.reqeust.UserUpdateRequest;
import com.jimwhere.api.user.dto.response.UserResponse;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public PageResponse<UserResponse> findAll(Pageable pageable) {

        Page<UserResponse> page = userRepository.findAll(pageable)
                .map(UserResponse::from);

        return PageResponse.of(page);
    }


    public UserResponse findMyInfo(String username) {

        User foundUser = userRepository.findByUserId(username).orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_USER_ID));
        return UserResponse.from(foundUser);
    }

    public UserResponse findUserByCode(long userCode) {
        User foundUser = userRepository.findById(userCode).orElseThrow(() ->
                new CustomException(ErrorCode.INVALID_USER_ID));

        return UserResponse.from(foundUser);
    }

    @Transactional
    public void updateUserPhoneNumber(String username, UserUpdatePhoneRequest request) {
        User foundUser = userRepository.findByUserId(username).orElseThrow(() ->
                new CustomException(ErrorCode.INVALID_USER_ID));
        boolean foundPhoneNumber = userRepository.existsByUserPhoneNumber(request.getNewPhoneNumber());

        if (foundUser.getUserPhoneNumber().equals(request.getNewPhoneNumber())) {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);
        }
        if (foundPhoneNumber) {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);
        }

        foundUser.updatePhone(request.getNewPhoneNumber());
    }

    @Transactional
    public void deactivateUser(String username) {
        User foundUser = userRepository.findByUserId(username).orElseThrow(() ->
                new CustomException(ErrorCode.INVALID_USER_ID));

        foundUser.modifyUserStatus(UserStatus.N);
    }

    @Transactional
    public void updateUserAdminSettings(long userCode, UserUpdateRequest request) {
        String rawStatus = request.getStatus();
        String rawRole = request.getRole();

        if ("".equals(rawStatus) || "".equals(rawRole)) {
            throw new CustomException(ErrorCode.INVALID_INCORRECT_FORMAT);
        }

        if (rawStatus == null && rawRole == null) {
            throw new CustomException(ErrorCode.INVALID_INCORRECT_FORMAT , "변경할 값을 입력해야 합니다");
        }

        User foundUser = userRepository.findById(userCode)
                .orElseThrow(() ->new CustomException(ErrorCode.INVALID_USER_ID));


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
