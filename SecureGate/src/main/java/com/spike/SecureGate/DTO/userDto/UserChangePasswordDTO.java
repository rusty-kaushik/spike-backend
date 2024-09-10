package com.spike.SecureGate.DTO.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordDTO {
    private String oldPassword;
    private String newPassword;

}