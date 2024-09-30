package com.spike.user.dto;

import com.spike.user.entity.Department;
import com.spike.user.entity.UserAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserProfileDTO {
    private String name;
    private String email;
    private String designation;
    private String employeeCode;
    private String username;
    private String role;
    private String primaryMobileNumber;
    private String joiningDate;
    private String salary;
    private Long managerId;
    private Set<Department> department;
    private List<UserAddress> addresses;
    private String profilePicture;
}
