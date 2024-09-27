package com.spike.SecureGate.service.externalPublicService;

import com.spike.SecureGate.DTO.publicDto.LoginResponseDTO;
import com.spike.SecureGate.DTO.publicDto.UserInfoDTO;

public interface PublicService {


    UserInfoDTO getUserByUsername(String username);

    LoginResponseDTO setLoginResponseDTO(UserInfoDTO userByUsername, String token);
}
