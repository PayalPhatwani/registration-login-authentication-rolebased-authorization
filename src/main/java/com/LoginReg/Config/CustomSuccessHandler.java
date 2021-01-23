package com.LoginReg.Config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

//After successfully login to redirect user based on their role
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectUrl = null;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			System.out.println("role " + grantedAuthority.getAuthority());
			// if user having role teacher redirect to teacher panel
			if (grantedAuthority.getAuthority().equals("ROLE_TEACHER")) {
				redirectUrl = "/teacher";
				break;
			}
			// if user having role student redirect to student panel
			else if (grantedAuthority.getAuthority().equals("ROLE_STUDENT")) {
				redirectUrl = "/student";
				break;
			}
		}
		System.out.println("redirectUrl " + redirectUrl);
		if (redirectUrl == null) {
			throw new IllegalStateException();
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}