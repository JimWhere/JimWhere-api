package com.jimwhere.api.user.repository;

import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.domain.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(String userId);
    Page<User> findByStatus(UserStatus status, Pageable pageable);

    boolean existsByUserId(String userId);
    boolean existsByUserPhoneNumber(String userPhoneNumber);
    boolean existsByUserBusinessNumber(String userBusinessNumber);

}
