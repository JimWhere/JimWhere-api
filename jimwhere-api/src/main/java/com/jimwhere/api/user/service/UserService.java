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

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public PageResponse<UserResponse> findAll(Pageable pageable , String status) {

        Page<User> usersPage;

        // 상태 필터 없는 경우 전체 조회
        if (status == null || status.isBlank()) {
            usersPage = userRepository.findAll(pageable);
        }
        // 상태 값이 올바르게 들어온 경우 상태별 조회
        else {
            try {
                UserStatus statusEnum = UserStatus.valueOf(status);
                usersPage = userRepository.findByStatus(statusEnum, pageable);
            } catch (IllegalArgumentException e) {
                throw new CustomException(ErrorCode.INVALID_INCORRECT_FORMAT, "잘못된 상태값입니다.");
            }
        }

        Page<UserResponse> page = usersPage.map(UserResponse::from);
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

        User foundUser = userRepository.findById(userCode)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_ID));

        String rawStatus = request.getStatus();
        String rawRole = request.getRole();

        // 아무 값도 없는 경우 → 에러
        if ((rawStatus == null || rawStatus.isBlank()) &&
                (rawRole == null || rawRole.isBlank())) {
            throw new CustomException(ErrorCode.INVALID_INCORRECT_FORMAT,
                    "변경할 값을 하나 이상 입력해야 합니다.");
        }


        // STATUS 수정
        if (rawStatus != null && !rawStatus.isBlank()) {
            try {
                UserStatus statusEnum = UserStatus.valueOf(rawStatus);
                foundUser.modifyUserStatus(statusEnum);
            } catch (Exception e) {
                throw new CustomException(ErrorCode.INVALID_INCORRECT_FORMAT,
                        "잘못된 상태(status) 값입니다.");
            }
        }

        // ROLE 수정
        if (rawRole != null && !rawRole.isBlank()) {
            try {
                UserRole roleEnum = UserRole.valueOf(rawRole);
                foundUser.modifyUserRole(roleEnum);
            } catch (Exception e) {
                throw new CustomException(ErrorCode.INVALID_INCORRECT_FORMAT,
                        "잘못된 역할(role) 값입니다.");
            }
        }
    }


    public Map<String, Long> getUserStatistics() {

        long total = userRepository.count();            // 전체
        long active = userRepository.countByStatus(UserStatus.Y);  // 활성
        long inactive = userRepository.countByStatus(UserStatus.N); // 삭제 or 정지

        Map<String, Long> result = new HashMap<>();
        result.put("total", total);
        result.put("active", active);
        result.put("inactive", inactive);

        return result;
    }


}
