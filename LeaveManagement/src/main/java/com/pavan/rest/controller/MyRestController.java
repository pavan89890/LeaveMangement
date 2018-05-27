package com.pavan.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.model.EmployeeBO;
import com.pavan.repository.EmployeeRepository;

@RestController
public class MyRestController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@PostMapping("/register")
	public String register(@ModelAttribute EmployeeBO employee) {
		String message = "";
		if (employee.getSecurityQuestion() == null) {
			message = "ERROR-Please Select Security Question.";
			return message;
		} else {
			List<EmployeeBO> dupEmpList = employeeRepository.findByEmailOrUsername(employee.getEmail(),
					employee.getUsername());

			if (dupEmpList.isEmpty()) {
				employeeRepository.save(employee);
				message = "SUCCESS-Hi " + employee.getFirstname() + " " + employee.getLastname()
						+ " your registration done successfully.Please login to continue.";
				return message;
			} else {
				message = "ERROR-You are already registered with this email or username";
				return message;
			}
		}
	}
}
