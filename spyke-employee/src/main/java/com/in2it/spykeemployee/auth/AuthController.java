package com.in2it.spykeemployee.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/in2it/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	
	private final AuthService service;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody ResisterRequest registerRequest){
		AuthenticationResponse authResponse = service.register(registerRequest);
		return ResponseEntity.ok(authResponse);
	}
	
	
	@PostMapping("/authenticate/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(service.authenticate(request));
	}
	
	
	
}
