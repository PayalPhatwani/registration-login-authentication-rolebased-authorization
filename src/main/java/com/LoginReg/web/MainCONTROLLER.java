package com.LoginReg.web;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.LoginReg.Modal.User;

@Controller
public class MainCONTROLLER {
	//getting username using principle concept in spring security
	static String getuserDetails() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			String username = ((UserDetails)principal).getUsername();
			return username;
		}else {
			String username = principal.toString();
			return username;
		}
	
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/student")
	public String studentPanel(HttpSession session) {
		String username = getuserDetails();
		//setting attribute of student in session to use in thymeleaf templates
		if(username != null && !username.isEmpty()) {
		session.setAttribute("student", 'S');
		}
		System.out.println("in student "+username);

		return "StudentWelcome";
	}
	
	@GetMapping("/teacher")
	public String teacherPanel(HttpSession session) {
		String username = getuserDetails();
		//setting attribute of teacher in session to use in thymeleaf templates
		if(username != null && !username.isEmpty()) {
		session.setAttribute("teacher", 'T');
		}
		System.out.println("in teacher "+username);
		return "TeacherWelcome";
	}
	
	@GetMapping("/")
	public String welcome() {
		return "homepage";
	}
	
	@GetMapping("/error")
	public String error() {
		return "error";
	}
	
	
}

