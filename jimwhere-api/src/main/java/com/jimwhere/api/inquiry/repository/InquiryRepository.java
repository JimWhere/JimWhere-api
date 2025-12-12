package com.jimwhere.api.inquiry.repository;

import com.jimwhere.api.inquiry.domain.Inquiry;
import com.jimwhere.api.user.domain.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    Optional<Inquiry> findByInquiryCodeAndIsDeletedFalse(Long inquiryCode);

    @EntityGraph(attributePaths = "user")
    Page<Inquiry> findByIsDeletedFalse(Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Page<Inquiry> findByUserAndIsDeletedFalse(User user, Pageable pageable);

    long countByInquiryAnswerIsNotNull();

    long countByInquiryAnswerIsNull();
}
