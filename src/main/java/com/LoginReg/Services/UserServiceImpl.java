package com.LoginReg.Services;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.LoginReg.Modal.Role;
import com.LoginReg.Modal.User;
import com.LoginReg.Repository.UserRepository;
import com.LoginReg.web.dto.UserRegistrationdto;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepo = userRepository;
	}

	@Override
	public User save(UserRegistrationdto userdto) {
		User user = new User(userdto.getFirstname(), userdto.getLastname(), userdto.getEmail(),
				passwordEncoder.encode(userdto.getPassword()), Arrays.asList(new Role(userdto.getRole())));
		return userRepo.save(user);
	}

	// for userDetailsService
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	// mapping roles to authorities
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
