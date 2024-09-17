package com.spike.SecureGate.Validations;

import com.spike.SecureGate.DTO.userDto.*;
import com.spike.SecureGate.JdbcHelper.UserDbService;
import com.spike.SecureGate.enums.IndianState;
import com.spike.SecureGate.feignClients.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class UserValidators {

    @Autowired
    private UserDbService userDbService;

    @Autowired
    private UserFeignClient userFeignClient;

    public boolean validateUserCreationDetails(UserCreationRequestDTO userRequest) {
        if (userRequest == null) {
            throw new IllegalArgumentException("Data is required");
        }

        // Validate name
        if (userRequest.getName() == null || userRequest.getName().isEmpty() || !userRequest.getName().matches("^[a-zA-Z0-9 ]*$")) {
            throw new IllegalArgumentException("Name cannot be null, empty, or contain special characters");
        }

        // Validate email
        if (userRequest.getEmail() == null || !isValidEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Validate backup email if present
        if (userRequest.getBackupEmail() != null && !isValidEmail(userRequest.getBackupEmail())) {
            throw new IllegalArgumentException("Invalid backup email format");
        }

        // Validate designation
        if (userRequest.getDesignation() == null || userRequest.getDesignation().isEmpty()) {
            throw new IllegalArgumentException("Designation cannot be null or empty");
        }

        // Validate employee code
        if (userRequest.getEmployeeCode() == null || userRequest.getEmployeeCode().isEmpty()) {
            throw new IllegalArgumentException("Employee code cannot be null or empty");
        }

        // Validate manager ID
        if(userRequest.getManagerId() != null) {
            if (userRequest.getManagerId() <= 0) {
                throw new IllegalArgumentException("Manager ID must be positive");
            }
        }

        // Validate role
        if (userRequest.getRole() == null || (!userRequest.getRole().equals("MANAGER") && !userRequest.getRole().equals("EMPLOYEE"))) {
            throw new IllegalArgumentException("Role must be either 'MANAGER' or 'EMPLOYEE'");
        }

        // Validate primary mobile number
        if (userRequest.getPrimaryMobileNumber() != null && !isValidPhoneNumber(userRequest.getPrimaryMobileNumber())) {
            throw new IllegalArgumentException("Invalid primary mobile number format");
        }

        if( userRequest.getSecondaryMobileNumber() != null ) {
            // Validate secondary mobile number
            if ( !isValidPhoneNumber(userRequest.getSecondaryMobileNumber())) {
                throw new IllegalArgumentException("Invalid secondary mobile number format");
            }
        }


        // Validate joining date
        if (userRequest.getJoiningDate() != null && userRequest.getJoiningDate().after(new Date())) {
            throw new IllegalArgumentException("Joining date must be in the past");
        }

        // Validate salary
        if (userRequest.getSalary() != null && userRequest.getSalary() < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        List<String> validDepartments = userDbService.getAllDepartmentNames();
        // Validate department names
        if (userRequest.getDepartment() != null && !userRequest.getDepartment().isEmpty()) {
            for (String department : userRequest.getDepartment()) {
                if (!validDepartments.contains(department)) {
                    throw new IllegalArgumentException("Invalid department: " + department);
                }
            }
        }

        // Validate addresses
        if (userRequest.getAddresses() != null && !userRequest.getAddresses().isEmpty()) {
            for (UserAddressDTO address : userRequest.getAddresses()) {
                validateAddress(address);
            }
        }
        return true;
    }

    // Method to validate address fields
    private void validateAddress(UserAddressDTO address) {
        if (address.getLine1() == null || address.getLine1().isEmpty()) {
            throw new IllegalArgumentException("Address Line1 cannot be null or empty");
        }
        if (address.getState() == null || address.getState().isEmpty()) {
            throw new IllegalArgumentException("State cannot be null or empty");
        }
        // Validate if the state is a valid state in India
        if (!IndianState.isValidState(address.getState())) {
            throw new IllegalArgumentException("Invalid state: " + address.getState() + ". Please provide a valid state in India.");
        }
        if (address.getDistrict() == null || address.getDistrict().isEmpty()) {
            throw new IllegalArgumentException("District cannot be null or empty");
        }
        if (address.getZip() == null || !address.getZip().matches("\\d{6}")) {
            throw new IllegalArgumentException("Invalid Zip Code format");
        }
        if (address.getCity() == null || address.getCity().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (address.getCountry() == null || address.getCountry().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        if (address.getType() == null || (!address.getType().equals("PERMANENT") && !address.getType().equals("CURRENT") )){
            throw new IllegalArgumentException("Address can be either 'PRIMARY' or 'TEMPORARY'");
        }

    }

    private boolean isValidEmail(String email) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return Pattern.compile("^[0-9]{10}$").matcher(phoneNumber).matches(); // ("^\\+?[0-9]{10,15}$")
    }


    public boolean validateUserUpdatePassword(UserChangePasswordDTO userChangePasswordDTO) {
        // TODO UNCOMMENT IT TO ENABLE PASSWORD VALIDATIONS
//        if (userChangePasswordDTO.getNewPassword() != null && !userChangePasswordDTO.getNewPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,20}$")) {
//            throw new IllegalArgumentException("Invalid password");
//        }
        return true;
    }

    public boolean validateUserSelfDetailsUpdate(UserFullUpdateDTO userUpdateRequestDTO) {

        // todo verify the uniqueness of the username

        if (userUpdateRequestDTO.getLinkedinUrl() != null && !userUpdateRequestDTO.getLinkedinUrl().matches("^https:\\/\\/linkedin\\.com\\/in\\/[a-zA-Z0-9\\-]+$")) {
            throw new IllegalArgumentException("Invalid LinkedIn URL");
        }

        // Validate Facebook URL
        if (userUpdateRequestDTO.getFacebookUrl() != null && !userUpdateRequestDTO.getFacebookUrl().matches("^https:\\/\\/facebook\\.com\\/[a-zA-Z0-9\\.]+$")) {
            throw new IllegalArgumentException("Invalid Facebook URL");
        }

        // Validate Instagram URL
        if (userUpdateRequestDTO.getInstagramUrl() != null && !userUpdateRequestDTO.getInstagramUrl().matches("^https:\\/\\/instagram\\.com\\/[a-zA-Z0-9_\\.]+$")) {
            throw new IllegalArgumentException("Invalid Instagram URL");
        }

        // validate backup email of user
        if (userUpdateRequestDTO.getBackupEmail() != null && !isValidEmail(userUpdateRequestDTO.getBackupEmail())) {
            throw new IllegalArgumentException("Invalid backup email format");
        }
        // Validate primary mobile number
        if (userUpdateRequestDTO.getPrimaryMobileNumber() != null && !isValidPhoneNumber(userUpdateRequestDTO.getPrimaryMobileNumber())) {
            throw new IllegalArgumentException("Invalid primary mobile number format");
        }
        // Validate secondary mobile number
        if (userUpdateRequestDTO.getSecondaryMobileNumber() != null && !isValidPhoneNumber(userUpdateRequestDTO.getSecondaryMobileNumber())) {
            throw new IllegalArgumentException("Invalid secondary mobile number format");
        }

        if (userUpdateRequestDTO.getAddresses() != null && !userUpdateRequestDTO.getAddresses().isEmpty()) {
            for (UserAddressDTO address : userUpdateRequestDTO.getAddresses()) {
                validateAddress(address);
            }
        }
        return true;
    }

    public boolean validateUserExistence(long userId) {
        ResponseEntity<Object> responseEntity = userFeignClient.getUserById(userId);
        return responseEntity.getStatusCode().value() == 200;
    }
}
