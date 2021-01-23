package com.LoginReg.Config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.LoginReg.Services.UserService;

//Configuring authentication
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	AuthenticationSuccessHandler successHandler;
	
	@Autowired
	private UserService userService;

	// for encrypting password
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(
				"/",
				"/registration**",
				"/js/**",
				"/css/**",
				"/img/**").permitAll() //permitting access to all url matches given regex 
		.antMatchers("/student").hasAnyAuthority("ROLE_STUDENT") //allow access to /student to the one's having ROLE_STUDENT
		.antMatchers("/teacher").hasAnyAuthority("ROLE_TEACHER") //allow access to /teacher to the one's having ROLE_TEACHER
		.anyRequest().authenticated() // url specified above will be authenticated
		.and()
		.formLogin()
		.loginPage("/login") // Specifies the login page URL
		.successHandler(successHandler) //Success handler invoked after successful authentication
		.permitAll() // Allow access to any URL associate to formLogin()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		.permitAll()  // Allow access to any URL associate to logout()
		.and()
		.exceptionHandling().accessDeniedPage("/error"); //if exception arises redirected to error page
	}
	
}
