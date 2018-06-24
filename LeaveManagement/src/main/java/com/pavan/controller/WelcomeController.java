package com.pavan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pavan.model.EmployeeBO;
import com.pavan.model.SecurityQuestionBO;
import com.pavan.repository.EmployeeRepository;
import com.pavan.repository.SecurityQuestionRepository;

@Controller
@SessionAttributes(names = "currentUser")
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
	public String login(@RequestParam String userName, @RequestParam String password, Model model) {
		String message = "";
		EmployeeBO currentUser = employeeRepository.findByUserNameAndPassword(userName, password);
		if (currentUser == null) {
			message = "Please Enter Correct Username Or Password.";
			model.addAttribute("message", message);
			return "login";
		} else {
			model.addAttribute("currentUser", currentUser);
			return "redirect:/employee/dashboard";
		}
	}

	@GetMapping("/logout")
	public String logout() {
		return "login";
	}

	@GetMapping("/employee/dashboard")
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

	@GetMapping("/employee/profile")
	public ModelAndView profile() {
		List<SecurityQuestionBO> securityQns = securityQuestionRepository.findAll();
		ModelAndView mv = new ModelAndView();
		mv.addObject("securityQns", securityQns);
		mv.setViewName("profile");
		return mv;
	}

	@GetMapping("/employee/managerMapping")
	public ModelAndView managerMapping(HttpSession session) {
		EmployeeBO currentUser = (EmployeeBO) session.getAttribute("currentUser");
		List<EmployeeBO> employees = employeeRepository.findAllExceptOne(currentUser.getId());
		ModelAndView mv = new ModelAndView();
		mv.addObject("employees", employees);
		mv.setViewName("managerMapping");
		return mv;
	}

	@PostMapping("/employee/saveProfilePic")
	public String saveProfilePic(HttpSession session, @RequestParam("profilePic") MultipartFile file) {
		if (!file.isEmpty() && file.getContentType().equals("image/jpeg")) {
			EmployeeBO currentUser = (EmployeeBO) session.getAttribute("currentUser");
			try {
				currentUser.setProfilePic(file.getBytes());
				employeeRepository.save(currentUser);
			} catch (IOException e) {
				e.printStackTrace();
				return e.getMessage();
			}
		} else {
			return "Please Upload Image image/jpeg file";
		}

		return "redirect:/employee/profile";
	}

}