package com.example.demo;

public class MemoConfig {
	
	//ハッシュ化
//	package com.example.demo.service;
//
//	import org.springframework.beans.factory.annotation.Autowired;
//	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//	import org.springframework.stereotype.Service;
//	import com.example.demo.entity.User;
//	import com.example.demo.mapper.UserMapper;
//
//	@Service
//	public class UserService {
//
//	    @Autowired
//	    private UserMapper userMapper;
//
//	    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//	    public void registerUser(String username, String rawPassword) {
//	        String encodedPassword = passwordEncoder.encode(rawPassword);
//	        User user = new User();
//	        user.setUsername(username);
//	        user.setPassword(encodedPassword);
//	        userMapper.insertUser(user);
//	    }
//	}

	//ハッシュ化
	
	//ログイン遷移ページ制限
	
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.security.web.SecurityFilterChain;
	import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

	@Configuration
	@EnableWebSecurity
	public class SecurityConfig {

	    private final CustomMemberServiceImpl customMemberService;

	    public SecurityConfig(CustomMemberServiceImpl customMemberService) {
	        this.customMemberService = customMemberService;
	    }

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
	        CustomUsernamePasswordAuthenticationFilter customFilter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager);

	        http
	            .authorizeHttpRequests(authorize -> authorize
	                .requestMatchers("/login", "/top", "/css/**", "/js/**", "/images/**").permitAll()
	                .requestMatchers("/customer/**").hasRole("CUSTOMER")
	                .requestMatchers("/employee/**").hasRole("EMPLOYEE")
	                .requestMatchers("/addMember").hasRole("EMPLOYEE")
	                .requestMatchers("/private").hasRole("CUSTOMER")
	                .anyRequest().authenticated()
	            )
	            .addFilterAt(customFilter, UsernamePasswordAuthenticationFilter.class)
	            .formLogin(formLogin -> formLogin
	                .loginPage("/login")
	                .defaultSuccessUrl("/home", true)
	                .failureHandler(new CustomAuthenticationFailureHandler("/login?error=true"))
	                .permitAll()
	            )
	            .logout(logout -> logout
	                .permitAll()
	            )
	            .userDetailsService(customMemberService)
	            .csrf().disable();

	        return http.build();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	}

	
	//ログイン遷移ページ制限
	
	//メンバーコードへ変更
	
	@Service
	public class CustomMemberServiceImpl implements UserDetailsService {

	    @Autowired
	    private MemberMapper memberMapper;

	    @Override
	    public UserDetails loadUserByUsername(String memberCode) throws UsernameNotFoundException {
	        Member member = memberMapper.findByMemberCode(memberCode);
	        if (member == null) {
	            throw new UsernameNotFoundException("User not found");
	        }
	        return new User(member.getMemberCode(), member.getPassword(), getAuthorities(member.getRole()));
	    }

	    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
	        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
	    }
	}

	//メンバーコードへ変更

}
