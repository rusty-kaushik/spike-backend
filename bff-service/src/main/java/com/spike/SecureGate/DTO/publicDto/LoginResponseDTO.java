package com.spike.SecureGate.DTO.publicDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private String token;
    private String designation;
    private String picture;
}
