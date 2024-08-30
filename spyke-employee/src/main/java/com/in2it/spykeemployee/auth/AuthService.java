package com.in2it.spykeemployee.auth;


import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.in2it.spykeemployee.config.JwtService;
import com.in2it.spykeemployee.entity.Employee;
import com.in2it.spykeemployee.repository.EmployeeRepository;
import com.in2it.spykeemployee.repository.RoleRepository;
import com.in2it.spykeemployee.service.EmployeeUserDetailsService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final EmployeeRepository repository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final RoleRepository roleRepository;
	private final EmployeeUserDetailsService detailsService;

	public AuthenticationResponse register(ResisterRequest registerRequest) {
		
		 var user = Employee.builder()
				 .firstName(registerRequest.getFirstName())
				 .lastName(registerRequest.getLastName())
				 .gender(registerRequest.getGender())
				 .password(passwordEncoder.encode(registerRequest.getPassword()))
				 .username(registerRequest.getUsername())
				 .status("ACTIVE")
				 .roles(roleRepository.findByName("NEW_JOINEE").stream().collect(Collectors.toSet()))
				 .build();
		 var savedUser = repository.save(user); 
		 UserDetails userDetails = detailsService.loadUserByUsername(savedUser.getUsername());
//		 String jwtTocken = jwtService.generateToken(savedUser);
		 String jwtTocken = jwtService.generateToken(userDetails);
		 return AuthenticationResponse.builder().accessToken(jwtTocken).username(savedUser.getUsername()).roles(savedUser.getRoles()).password(registerRequest.getPassword()).build();
	
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request){
		// First Step(Create been for all)
			// We need to validate our request(validate whether password & username is correct)
			// Verify whether user present in data base
			// Which authentication provider -> DaoAuthenticationProvider(inject)
			// We need to authenticate using authentication manager injecting this authentication provider
		// Second Step
			//Verify whether userName and password is correct or not => UserNamePasswordAuthenticationToken
			//verify whether user present in db
			// Generate Token
			// Return the token
		try {
		
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (Exception e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}
		var user = repository.findByUsername(request.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User Dosen't Exist"));
		UserDetails userDetails = detailsService.loadUserByUsername(user.getUsername());
		String jwtTocken = jwtService.generateToken(userDetails);
		return AuthenticationResponse.builder().accessToken(jwtTocken).username(user.getUsername()).password("**************").roles(user.getRoles()).build();
	}

}
