package com.spike.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSocialDTO {
    private String linkedinUrl;
    private String facebookUrl;
    private String instagramUrl;
}
