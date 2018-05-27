package com.pavan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pavan.model.EmployeeBO;
import com.pavan.model.SecurityQuestionBO;
import com.pavan.repository.EmployeeRepository;
import com.pavan.repository.SecurityQuestionRepository;

@Controller
@SessionAttributes(names="currentUser")
public class WelcomeController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SecurityQuestionRepository securityQuestionRepository;

	@GetMapping("/")
	public String index() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		String message = "";
		EmployeeBO currentUser = employeeRepository.findByUsernameAndPassword(username, password);
		if (currentUser == null) {
			message = "Please Enter Correct Username Or Password.";
			model.addAttribute("message", message);
			return "login";
		} else {
			model.addAttribute("currentUser", currentUser);
			return "redirect:/dashboard";
		}
	}

	@GetMapping("/logout")
	public String logout() {
		return "login";
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

	@GetMapping("/register")
	public ModelAndView register() {
		List<SecurityQuestionBO> securityQns = securityQuestionRepository.findAll();
		ModelAndView mv = new ModelAndView();
		mv.addObject("securityQns", securityQns);
		mv.setViewName("register");
		return mv;
	}
	
	@GetMapping("/profile")
	public String profile() {
		return "profile";
	}

}
