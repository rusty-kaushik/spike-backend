package com.spike.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFullUpdateDTO {
    private String username;
    private String backupEmail;
    private String primaryMobileNumber;
    private String secondaryMobileNumber;
    private String linkedinUrl;
    private String facebookUrl;
    private String instagramUrl;
    private List<UserAddressDTO> addresses;
}
