package com.spike.SecureGate.DTO.publicDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String username;
    private String password;
    private String roleName;
}
