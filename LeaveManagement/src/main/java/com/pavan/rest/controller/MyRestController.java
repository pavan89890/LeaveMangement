package com.pavan.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pavan.model.EmployeeBO;
import com.pavan.repository.EmployeeRepository;

@RestController
@SessionAttributes(names = "currentUser")
public class MyRestController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@PostMapping("/register")
	public String register(@ModelAttribute EmployeeBO employee, Model model) {
		String message = "";
		if (employee.getSecurityQuestion() == null) {
			message = "ERROR-Please Select Security Question.";
			return message;
		} else {
			List<EmployeeBO> dupEmpList = employeeRepository.findByEmailOrUserName(employee.getEmail(),
					employee.getUserName());

			if (employee.getId() == 0 && dupEmpList.isEmpty()) {
				employeeRepository.save(employee);
				message = "SUCCESS-Hi " + employee.getFirstName() + " " + employee.getLastName()
						+ " your registration done successfully.Please login to continue.";
				return message;
			} else if (employee.getId() > 0 && dupEmpList.size() == 1) {
				EmployeeBO e = employeeRepository.findOne(employee.getId());
				employee.setProfilePic(e.getProfilePic());
				employeeRepository.save(employee);
				message = "SUCCESS-Hi " + employee.getFirstName() + " " + employee.getLastName()
						+ " your profile saved successfully.";
				model.addAttribute("currentUser", employee);
				return message;
			} else {
				message = "ERROR-You are already registered with this email or username";
				return message;
			}
		}
	}

	@PostMapping("/managerMapping")
	public String managerMapping(@ModelAttribute("emp") EmployeeBO emp) {

		for (EmployeeBO e : emp.getEmployees()) {
			e.setManager(emp);
			employeeRepository.save(e);
		}
		return "SUCCESS-Successfully Done.";
	}

	@RequestMapping(value = "/employee/getImage/{emp}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable("emp") EmployeeBO emp) {
		return emp.getProfilePic();
	}
}
