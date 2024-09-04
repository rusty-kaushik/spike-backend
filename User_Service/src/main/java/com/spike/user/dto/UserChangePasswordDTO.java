package com.spike.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
