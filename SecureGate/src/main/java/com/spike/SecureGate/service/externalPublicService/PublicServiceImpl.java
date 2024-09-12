package com.spike.SecureGate.service.externalPublicService;

import com.spike.SecureGate.DTO.publicDto.LoginResponseDTO;
import com.spike.SecureGate.DTO.publicDto.UserInfoDTO;
import com.spike.SecureGate.feignClients.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicServiceImpl implements PublicService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserInfoDTO getUserByUsername(String username) {
        return userFeignClient.getUserByUsername(username);
    }

    @Override
    public LoginResponseDTO setLoginResponseDTO(UserInfoDTO userByUsername, String token) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setDesignation(userByUsername.getDesignation());
        loginResponseDTO.setToken(token);
        loginResponseDTO.setPicture(userByUsername.getPicture());
        return loginResponseDTO;
    }
}
