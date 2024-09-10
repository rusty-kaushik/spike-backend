package com.spike.SecureGate.service;

import com.spike.SecureGate.DTO.publicDto.JwtDTO;
import com.spike.SecureGate.JdbcHelper.UserDbService;
import com.spike.SecureGate.controllers.PublicController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private UserDbService userDbService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Started fetching user credentials");
        JwtDTO jwtDTO = userDbService.getUserByUsername(username);
        logger.info("User credentials fetched");
        if (jwtDTO != null) {
            return User.builder()
                    .username(jwtDTO.getUsername())
                    .password(jwtDTO.getPassword())
                    .roles(jwtDTO.getRoleName())
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

}