package com.jimwhere.api.inquiry.repository;

import com.jimwhere.api.inquiry.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

}
