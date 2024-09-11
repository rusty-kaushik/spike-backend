package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.publicDto.LoginRequestDTO;
import com.spike.SecureGate.response.ResponseHandler;
import com.spike.SecureGate.service.UserDetailsServiceImpl;
import com.spike.SecureGate.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/in2it/spike/SecureGate/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Operation(
            summary = "Login to the Project",
            description = "Logs in a user. The API takes username and password in json and return a JWT token which is used to call each api.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully logged in",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(schema = @Schema()) })
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            logger.info("Processing login request for user: {}", loginRequestDTO.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getUsername());
            logger.info("User successfully logged in");
            return ResponseHandler.responseBuilder("USER LOGIN SUCCESSFUL", HttpStatus.OK, jwtUtil.generateToken(userDetails.getUsername()));
        } catch (Exception e) {
            logger.error("Error while processing login request for user: {}", loginRequestDTO.getUsername());
            return ResponseHandler.responseBuilder("USER LOGIN UNSUCCESSFUL", HttpStatus.UNAUTHORIZED, "Wrong username or password, Please try again.");
        }
    }

}
