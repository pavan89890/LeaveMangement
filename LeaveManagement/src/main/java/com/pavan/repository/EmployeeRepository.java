package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.model.EmployeeBO;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeBO, Long> {
	public List<EmployeeBO> findByEmailOrUsername(String email,String username);
	public EmployeeBO findByUsernameAndPassword(String userName,String password);
}
