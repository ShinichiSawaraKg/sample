//package com.example.demo.config;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//	public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
//		super.setAuthenticationManager(authenticationManager);
//	}
//
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException {
//		String memberCodeChar = request.getParameter("username");
//		String password = request.getParameter("password");
//
//		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(memberCodeChar, password);
//
//		setDetails(request, authRequest);
//		return this.getAuthenticationManager().authenticate(authRequest);
//	}
//
//}
