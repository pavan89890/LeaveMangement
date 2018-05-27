package com.pavan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.model.SecurityQuestionBO;

@Repository
public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestionBO, Long> {
	public SecurityQuestionBO findById(long id);
}
