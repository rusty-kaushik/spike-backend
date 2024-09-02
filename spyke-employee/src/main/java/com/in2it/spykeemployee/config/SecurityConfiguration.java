package com.in2it.spykeemployee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.in2it.spykeemployee.exception.JwtAccessDeniedHandler;
import com.in2it.spykeemployee.exception.JwtAuthEntryPoint;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@AllArgsConstructor
@Slf4j
public class SecurityConfiguration {
	
	
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthFilter jwtAuthFilter;
	private final JwtAuthEntryPoint jwtAuthEntryPoint;
	private final JwtAccessDeniedHandler accessDeniedHandler;
	private final RoleHierarchy roleHierarchy;

	private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
			"/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/auth/**",
			"/api/test/**", "/authenticate" ,"register"};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(req ->
		req.requestMatchers("/in2it/v1/auth/**").permitAll()
				.requestMatchers(WHITE_LIST_URL).permitAll()
				.requestMatchers("in2it/v1/auth/register").permitAll()
				.requestMatchers("in2it/employees/roles").hasRole("SUPER_ADMIN")
				.requestMatchers("in2it/employees/roles/permissions").hasRole("SUPER_ADMIN")
				.requestMatchers("in2it/departments").hasRole("SUPER_ADMIN")
//				.requestMatchers("in2it/employees/**").hasAnyRole("SUPER_ADMIN","ADMIN","EMPLOYEE")
				.requestMatchers(HttpMethod.GET,"in2it/employees/**").hasAnyAuthority("READ_PRIVILEGES")
//				.requestMatchers(HttpMethod.GET,"in2it/departments/**").hasAnyAuthority("READ_PRIVILEGES")
				.requestMatchers(HttpMethod.POST,"in2it/employees/**").hasAnyAuthority("WRITE_PRIVILEGES")
				.anyRequest().authenticated()
				)

		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
		.exceptionHandling(e->e.accessDeniedHandler(accessDeniedHandler))

				.build();
	}

	
}



