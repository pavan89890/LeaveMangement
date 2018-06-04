package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavan.model.EmployeeBO;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeBO, Long> {
	public List<EmployeeBO> findByEmailOrUserName(String email, String userName);

	public EmployeeBO findByUserNameAndPassword(String userName, String password);

	@Query(value = "select e from EmployeeBO e where e.id not in(:id)")
	public List<EmployeeBO> findAllExceptOne(@Param("id") Long id);
}
