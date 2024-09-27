package com.spike.SecureGate.DTO.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDTO {
    private String username;
    private String backupEmail;
    private String primaryMobileNumber;
    private String secondaryMobileNumber;
}
