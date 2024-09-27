package com.spike.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequestDTO {
    private String name;
    private String email;
    private String backupEmail;
    private String designation;
    private String employeeCode;
    private Long managerId;
    private String role;
    private String primaryMobileNumber;
    private String secondaryMobileNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joiningDate;
    private Double salary;
    private List<String> department;
    private String linkedinUrl;
    private String facebookUrl;
    private String instagramUrl;
    private List<UserAddressDTO> addresses;
}
