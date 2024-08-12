package com.blog.controller.config;

import com.blog.controller.filter.JwtFilter;
import com.blog.service.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// *The SecurityConfig class configures security settings for the application.
// *It sets up rules for HTTP request access, integrates a custom JWT filter for authentication, and defines how user details and passwords are managed.
// *It ensures secure login, access control, and password handling using Spring Security.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    //* Configures security rules for HTTP requests.
    //* Allows public access to specific URLs, requires authentication for others, and adds a custom JWT filter for token validation.
    //* Disables CSRF protection but can be re-enabled if necessary.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request -> request
                        .requestMatchers("/in2it/blog/public/**").permitAll()
                        .requestMatchers("/in2it/blog/team/**").hasRole("ADMIN")
                        .requestMatchers("/in2it/blog/user","/in2it/blogs/**").authenticated()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //* Sets up global authentication settings by specifying a custom user details service and password encoder.
    //* This ensures user details are retrieved correctly and passwords are securely hashed for authentication.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //* Provides a BCryptPasswordEncoder for hashing passwords.
    //* This secure encoding method ensures that passwords are stored in a way that protects against unauthorized access and brute force attacks.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //* Exposes an AuthenticationManager bean for handling authentication tasks.
    //* Retrieves the authentication manager from Spring’s configuration to manage user login and credentials verification processes.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}