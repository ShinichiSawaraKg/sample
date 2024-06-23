package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/js/**", "/css/**", "/images/**", "/goSearchItem").permitAll()
//                .requestMatchers("/test").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/goSearchItem", true)
                .failureUrl("/login?error=true")
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/goSearchItem")
                .invalidateHttpSession(true) // セッションの無効化
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("CM00001")
            .password("{noop}pass")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}



//
//
////package com.example.demo.config;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////
////import com.example.demo.service.LoginMemberServiceImpl;
////
////@Configuration
////@EnableWebSecurity
////public class SecurityConfig {
////
////    private final LoginMemberServiceImpl loginMemberService;
////
////    public SecurityConfig(LoginMemberServiceImpl loginMemberService) {
////        this.loginMemberService = loginMemberService;
////    }
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
////        CustomUsernamePasswordAuthenticationFilter customFilter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager);
////
////        http
////            .authorizeHttpRequests(authorize -> authorize
////                .requestMatchers("/login", "/", "/goSearchItem", "/style.css/**", "/js/**", "/sampleImage.webp/**", "/script.js", "/images/**", "/test").permitAll()
//////                .requestMatchers("/customer/**").hasRole("CUSTOMER")
//////                .requestMatchers("/employee/**").hasRole("EMPLOYEE")
//////                .requestMatchers("/addMember").hasRole("EMPLOYEE")
//////                .requestMatchers("/private").hasRole("CUSTOMER")
////                .anyRequest().authenticated()
////            )
////            .addFilterAt(customFilter, UsernamePasswordAuthenticationFilter.class)
////            .formLogin(formLogin -> formLogin
////                .loginPage("/login")
////                .defaultSuccessUrl("/goSearchItem", true)
////                .failureHandler(new CustomAuthenticationFailureHandler("/login?error=true"))
////                .permitAll()
////            )
////            .logout(logout -> logout
////        		.logoutUrl("/logout")
////                .deleteCookies("JSESSIONID")
////                .logoutSuccessUrl("/")
////            )
////            .userDetailsService(loginMemberService);
//////            .csrf().disable();
////
////        return http.build();
////    }
////
//////    @Bean
//////    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//////        return authenticationConfiguration.getAuthenticationManager();
//////    }
////
//////    @Bean
//////    public PasswordEncoder passwordEncoder() {
//////        return new BCryptPasswordEncoder();
//////    }
////}
