package com.example.assignment1.config;

import com.example.assignment1.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;


import java.io.IOException;

/**
 * Configuration class for security settings.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Provides -> PasswordEncoder bean for encryption of  passwords.
     *
     * @return The PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides -> UserDetailsService bean for loading user data.
     *
     * @param repo The UserRepository instance.
     * @return The UserDetailsService bean.
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository repo) {
        return username -> repo.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    /**
     * Provides -> SecurityFilterChain bean for configuring security filters.
     *
     * @param http The HttpSecurity instance.
     * @return The SecurityFilterChain bean.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/signup/**", "/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/courses").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/courses/new", "/courses/delete/**", "/courses/edit/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/login")
                        .defaultSuccessUrl("/courses", true)
                        .permitAll())
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(accessDeniedHandler()));
        return http.build();
    }
    /**
     * Custom implementation of AccessDeniedHandler.
     */
    static class CustomAccessDeniedHandler implements AccessDeniedHandler {

        @Override
        public void handle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, jakarta.servlet.ServletException {
            request.setAttribute("errorMessage", "You don't have access for this process");

            // Forward the request back to the original page
            request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
        }
    }

    /**
     * Provides an AccessDeniedHandler bean.
     *
     * @return The AccessDeniedHandler bean.
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}



