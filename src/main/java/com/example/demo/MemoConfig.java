package com.example.demo;

public class MemoConfig {
	
//ページングの...機能の実装
	   <div th:if="${products}">
       <h2>Search Results</h2>
       <ul>
           <li th:each="product : ${products}">
               <span th:text="${product.name}">Product Name</span> - <span th:text="${product.description}">Product Description</span>
           </li>
       </ul>

       <div>
           <!-- ページネーションの表示 -->
           <nav>
               <ul>
                   <li th:if="${currentPage > 1}">
                       <a th:href="@{/travel-products/search(date=${date}, prefecture=${prefecture}, page=${currentPage - 1}, size=${size}, productType=${productType})}">Previous</a>
                   </li>
                   <li th:each="i : ${#numbers.sequence(1, totalPages)}" th:if="${i <= 3 or i > totalPages - 3 or (i >= currentPage - 1 and i <= currentPage + 1)}">
                       <a th:href="@{/travel-products/search(date=${date}, prefecture=${prefecture}, page=${i}, size=${size}, productType=${productType})}"
                          th:text="${i}"
                          th:classappend="${i == currentPage ? 'active' : ''}"></a>
                   </li>
                   <li th:if="${currentPage > 4}">
                       <span>...</span>
                   </li>
                   <li th:if="${totalPages > 6 and currentPage < totalPages - 2}">
                       <span>...</span>
                   </li>
                   <li th:if="${currentPage < totalPages}">
                       <a th:href="@{/travel-products/search(date=${date}, prefecture=${prefecture}, page=${currentPage + 1}, size=${size}, productType=${productType})}">Next</a>
                   </li>
               </ul>
           </nav>
       </div>
       </div>
       
//ページングCSS
       /* style.css */
       ul {
           list-style-type: none;
           padding: 0;
       }

       ul li {
           display: inline;
           margin-right: 5px;
       }

       ul li a {
           text-decoration: none;
           padding: 5px 10px;
           border: 1px solid #ddd;
           border-radius: 3px;
           color: #333;
       }

       ul li a.active {
           background-color: #007bff;
           color: white;
       }

       ul li span {
           padding: 5px 10px;
       }

/////////////////////////////////////////ログイン,ログアウト後の通知処理

//SecurityConfig
       
       import org.springframework.context.annotation.Bean;
       import org.springframework.context.annotation.Configuration;
       import org.springframework.security.config.annotation.web.builders.HttpSecurity;
       import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
       import org.springframework.security.core.userdetails.UserDetailsService;
       import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
       import org.springframework.security.crypto.password.PasswordEncoder;
       import org.springframework.security.web.SecurityFilterChain;
       import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
       import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

       @Configuration
       @EnableWebSecurity
       public class SecurityConfig {

           @Bean
           public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
               http
                   .authorizeHttpRequests(authorize -> authorize
                       .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                       .requestMatchers("/customer/**").hasRole("CUSTOMER")
                       .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                       .anyRequest().authenticated()
                   )
                   .formLogin(form -> form
                       .loginPage("/login")
                       .loginProcessingUrl("/perform_login")
                       .successHandler(authenticationSuccessHandler())
                       .failureUrl("/login?error=true")
                       .permitAll()
                   )
                   .logout(logout -> logout
                       .logoutUrl("/perform_logout")
                       .logoutSuccessHandler(logoutSuccessHandler())
                       .deleteCookies("JSESSIONID")
                       .permitAll()
                   );

               return http.build();
           }

           @Bean
           public PasswordEncoder passwordEncoder() {
               return new BCryptPasswordEncoder();
           }

           @Bean
           public UserDetailsService userDetailsService() {
               return new CustomMemberServiceImpl();
           }

           @Bean
           public AuthenticationSuccessHandler authenticationSuccessHandler() {
               return (request, response, authentication) -> {
                   request.getSession().setAttribute("loginSuccessMessage", "ログインしました。");
                   response.sendRedirect(request.getContextPath() + "/home");
               };
           }

           @Bean
           public LogoutSuccessHandler logoutSuccessHandler() {
               return (request, response, authentication) -> {
                   request.getSession().setAttribute("logoutSuccessMessage", "ログアウトしました。");
                   response.sendRedirect(request.getContextPath() + "/");
               };
           }
       }
       
       
//SecurityConfig       


       
//ClearMessageController.java
       
       import org.springframework.stereotype.Controller;
       import org.springframework.web.bind.annotation.PostMapping;

       import javax.servlet.http.HttpSession;

       @Controller
       public class ClearMessageController {

           @PostMapping("/clear-login-message")
           public void clearLoginMessage(HttpSession session) {
               session.removeAttribute("loginSuccessMessage");
           }

           @PostMapping("/clear-logout-message")
           public void clearLogoutMessage(HttpSession session) {
               session.removeAttribute("logoutSuccessMessage");
           }
       }

       
//ClearMessageController.java
       
       
//home.html ログイン成功後

       <!DOCTYPE html>
       <html xmlns:th="http://www.thymeleaf.org">
       <head>
           <title>Home Page</title>
           <link rel="stylesheet" type="text/css" th:href="@{/css/styles.css}" />
           <script th:inline="javascript">
               /*<![CDATA[*/
               var loginSuccessMessage = /*[[${session.loginSuccessMessage != null}]]*/ false;
               if (loginSuccessMessage) {
                   alert('/*[[${session.loginSuccessMessage}]]*/');
                   /* Clear the message */
                   fetch('/clear-login-message', { method: 'POST' });
               }
               /*]]>*/
           </script>
       </head>
       <body>
           <h1>Home Page</h1>
           <p>Welcome to the home page!</p>
       </body>
       </html>

       
//home.html　ログイン成功後
       
//top.html ログアウト成功後

       <!DOCTYPE html>
       <html xmlns:th="http://www.thymeleaf.org">
       <head>
           <title>Welcome Page</title>
           <link rel="stylesheet" type="text/css" th:href="@{/css/styles.css}" />
           <script th:inline="javascript">
               /*<![CDATA[*/
               var logoutSuccessMessage = /*[[${session.logoutSuccessMessage != null}]]*/ false;
               if (logoutSuccessMessage) {
                   alert('/*[[${session.logoutSuccessMessage}]]*/');
                   /* Clear the message */
                   fetch('/clear-logout-message', { method: 'POST' });
               }
               /*]]>*/
           </script>
       </head>
       <body>
           <h1>Welcome Page</h1>
           <p>Welcome to the website!</p>
       </body>
       </html>


//top.html       ログアウト成功後
       
//CustomAuthenticationSuccessHandler.java

       import org.springframework.security.core.Authentication;
       import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
       import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
       import org.springframework.security.web.savedrequest.RequestCache;
       import org.springframework.security.web.savedrequest.SavedRequest;
       import org.springframework.stereotype.Component;

       import javax.servlet.http.HttpServletRequest;
       import javax.servlet.http.HttpServletResponse;
       import java.io.IOException;

       @Component
       public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

           private final RequestCache requestCache = new HttpSessionRequestCache();

           @Override
           public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
               SavedRequest savedRequest = requestCache.getRequest(request, response);

               if (savedRequest == null) {
                   response.sendRedirect(request.getContextPath() + "/home");
                   return;
               }

               String targetUrl = savedRequest.getRedirectUrl();
               response.sendRedirect(targetUrl);
           }
       }


//CustomAuthenticationSuccessHandler.java
       
       
}
