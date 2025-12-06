package com.jimwhere.api.box.repository;

import com.jimwhere.api.box.domain.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
}
