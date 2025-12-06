package com.jimwhere.api.box.repository;

import com.jimwhere.api.box.domain.BoxType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxTypeRepository extends JpaRepository<BoxType, Long> {
}
