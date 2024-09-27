package com.spike.SecureGate.DTO.publicDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {

    private long id;
    private String username;
    private String role;
    private String email;
    private String designation;
    private String picture;
}
