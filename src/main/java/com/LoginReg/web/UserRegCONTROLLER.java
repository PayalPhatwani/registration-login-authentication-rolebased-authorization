package com.LoginReg.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.LoginReg.Services.UserService;
import com.LoginReg.Services.UserServiceImpl;
import com.LoginReg.web.dto.UserRegistrationdto;

@Controller
@RequestMapping("/registration")
public class UserRegCONTROLLER {

	private UserServiceImpl userservice;

	public UserRegCONTROLLER(UserServiceImpl userService) {
		super();
		this.userservice = userService;
	}

	@ModelAttribute("user")
	public UserRegistrationdto userRegistrationDto() {
		return new UserRegistrationdto();
	}

	@GetMapping
	public String showRegForm() {
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationdto regDto) {
		System.out.println("controller post method");
		userservice.save(regDto);
		return "redirect:/registration?success";
	}

}
