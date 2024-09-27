package com.spike.SecureGate.DTO.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSocialUpdateDTO {
    private String linkedinUrl;
    private String facebookUrl;
    private String instagramUrl;
}
