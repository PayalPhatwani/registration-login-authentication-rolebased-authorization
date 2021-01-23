package com.LoginReg.Services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.LoginReg.Modal.User;
import com.LoginReg.web.dto.UserRegistrationdto;

public interface UserService extends UserDetailsService {

	User save(UserRegistrationdto userdto);
}
