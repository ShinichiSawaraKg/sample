//package com.example.demo.config;
//
//import java.io.IOException;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//	private final String failureUrl;
//
//	public CustomAuthenticationFailureHandler(String failureUrl) {
//		this.failureUrl = failureUrl;
//	}
//
//	@Override
//	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException exception) throws IOException, ServletException {
//		response.sendRedirect(failureUrl);
//	}
//
//}
