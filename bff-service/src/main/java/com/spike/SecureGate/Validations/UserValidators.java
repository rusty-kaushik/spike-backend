package com.spike.SecureGate.Validations;

import com.spike.SecureGate.DTO.userDto.*;
import com.spike.SecureGate.JdbcHelper.UserDbService;
import com.spike.SecureGate.exceptions.ValidationFailedException;
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
            throw new ValidationFailedException(
                    "ValidationError",
                    "Data is required"
            );
        }

        // Validate name (name cannot be null or empty, and no special characters allowed)
        if (userRequest.getName() == null || userRequest.getName().isEmpty() || !userRequest.getName().matches("^[a-zA-Z0-9 ]*$")) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Name cannot be null, empty, or contain special characters"
            );
        }

        // Validate email (email cannot be null and must be valid)
        if (userRequest.getEmail() == null || !isValidEmail(userRequest.getEmail())) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Email cannot be null and must have a valid format"
            );
        }

        // Validate employee code (employee code cannot be null or empty)
        if (userRequest.getEmployeeCode() == null || userRequest.getEmployeeCode().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Employee code cannot be null or empty"
            );
        }

        // Validate role (role must be either 'MANAGER' or 'EMPLOYEE', cannot be null)
        if (userRequest.getRole() == null || (!userRequest.getRole().equals("MANAGER") && !userRequest.getRole().equals("EMPLOYEE"))) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Role must be either 'MANAGER' or 'EMPLOYEE'"
            );
        }

        // Validate primary mobile number (must not be null and must be valid)
        if (userRequest.getPrimaryMobileNumber() == null || !isValidPhoneNumber(userRequest.getPrimaryMobileNumber())) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Primary mobile number cannot be null and must have a valid format"
            );
        }

        // Validate joining date (must not be null and must be in the past)
        if (userRequest.getJoiningDate() == null || userRequest.getJoiningDate().after(new Date())) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Joining date cannot be null and must be in the past"
            );
        }

        // Validate salary (salary must not be null and must be non-negative)
        if (userRequest.getSalary() == null || userRequest.getSalary() < 0) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Salary cannot be null or negative"
            );
        }

        // Validate department (mandatory and must contain valid departments)
        List<String> validDepartments = userDbService.getAllDepartmentNames();
        if (userRequest.getDepartment() == null || userRequest.getDepartment().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Department is required and cannot be empty"
            );
        } else {
            for (String department : userRequest.getDepartment()) {
                if (!validDepartments.contains(department)) {
                    throw new ValidationFailedException(
                            "ValidationError",
                            "Invalid department: " + department
                    );
                }
            }
        }

        // Validate optional fields

        // Validate backup email if present
        if (userRequest.getBackupEmail() != null && !isValidEmail(userRequest.getBackupEmail())) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Invalid backup email format"
            );
        }

        // Validate designation if present
        if (userRequest.getDesignation() != null && userRequest.getDesignation().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Designation cannot be empty"
            );
        }

        // Validate manager ID if present (skip if null, must be positive if provided)
        if (userRequest.getManagerId() != null && !validateUserExistence(userRequest.getManagerId())) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Manager ID must be positive"
            );
        }

        // Validate secondary mobile number if present
        if (userRequest.getSecondaryMobileNumber() != null && !isValidPhoneNumber(userRequest.getSecondaryMobileNumber())) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Invalid secondary mobile number format"
            );
        }

        // Validate addresses if present
        if (userRequest.getAddresses() == null || userRequest.getAddresses().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "At least one address is required."
            );
        }

        if (userRequest.getAddresses().size() > 2) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "A maximum of 2 addresses is allowed."
            );
        }

        // Validate each address
        for (UserAddressDTO address : userRequest.getAddresses()) {
            validateAddress(address);
        }

        return true;
    }



    // Method to validate address fields
    private void validateAddress(UserAddressDTO address) {
        if (address == null) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Address cannot be null"
            );
        }

        // Validate Line1 (mandatory)
        if (address.getLine1() == null || address.getLine1().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Address Line1 cannot be null or empty"
            );
        }

        // Validate State (mandatory and must be valid)
        if (address.getState() == null || address.getState().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "State cannot be null or empty"
            );
        }

        // Validate City (mandatory)
        if (address.getCity() == null || address.getCity().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "City cannot be null or empty"
            );
        }

        // Validate Zip Code (mandatory, must follow the format)
        if (address.getZip() == null ||  address.getZip().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "ZIP cannot be null or empty"
            );
        }

        // Validate Country (mandatory)
        if (address.getCountry() == null || address.getCountry().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Country cannot be null or empty"
            );
        }

        // Validate Address Type (mandatory, must be either 'PERMANENT' or 'CURRENT')
        if (address.getType() == null || (!address.getType().equals("PERMANENT") && !address.getType().equals("CURRENT"))) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Address type must be either 'PERMANENT' or 'CURRENT'"
            );
        }

        // Optional fields, validate only if provided

        // District (optional)
        if (address.getDistrict() != null && address.getDistrict().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "District cannot be empty if provided"
            );
        }
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return Pattern.compile("^[0-9]{10}$").matcher(phoneNumber).matches(); // ("^\\+?[0-9]{10,15}$")
    }


    public void validateUserUpdatePassword(UserChangePasswordDTO userChangePasswordDTO) {
        if (userChangePasswordDTO.getNewPassword() != null && !userChangePasswordDTO.getNewPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,20}$")) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Invalid password"
            );
        }
    }

    public boolean validateUserSelfDetailsUpdate(UserFullUpdateDTO userUpdateRequestDTO) {

        // todo verify the uniqueness of the username

        if (userUpdateRequestDTO.getLinkedinUrl() != null && !userUpdateRequestDTO.getLinkedinUrl().matches("^https:\\/\\/linkedin\\.com\\/in\\/[a-zA-Z0-9\\-]+$")) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Invalid LinkedIn URL"
            );
        }

        // Validate Facebook URL
        if (userUpdateRequestDTO.getFacebookUrl() != null && !userUpdateRequestDTO.getFacebookUrl().matches("^https:\\/\\/facebook\\.com\\/[a-zA-Z0-9\\.]+$")) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Invalid Facebook URL"
            );
        }

        // Validate Instagram URL
        if (userUpdateRequestDTO.getInstagramUrl() != null && !userUpdateRequestDTO.getInstagramUrl().matches("^https:\\/\\/instagram\\.com\\/[a-zA-Z0-9_\\.]+$")) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Invalid Instagram URL"
            );
        }

        // validate backup email of user
        if (userUpdateRequestDTO.getBackupEmail() != null && !isValidEmail(userUpdateRequestDTO.getBackupEmail())) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Invalid backup email format"
            );
        }
        // Validate primary mobile number
        if (userUpdateRequestDTO.getPrimaryMobileNumber() != null && !isValidPhoneNumber(userUpdateRequestDTO.getPrimaryMobileNumber())) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Invalid primary mobile number format"
            );
        }
        // Validate secondary mobile number
        if (userUpdateRequestDTO.getSecondaryMobileNumber() != null && !isValidPhoneNumber(userUpdateRequestDTO.getSecondaryMobileNumber())) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Invalid secondary mobile number format"
            );
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

    public boolean validateUserContactCreation(ContactCreationRequestDTO contactCreationRequestDTO) {
        if (contactCreationRequestDTO == null) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Data is required"
            );
        }

        // Validate name (name cannot be null or empty, and no special characters allowed)
        if (contactCreationRequestDTO.getName() == null || contactCreationRequestDTO.getName().isEmpty() || !contactCreationRequestDTO.getName().matches("^[a-zA-Z0-9 ]*$")) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Name cannot be null, empty, or contain special characters"
            );
        }

        // Validate primary mobile number (must not be null and must be valid)
        if (contactCreationRequestDTO.getPrimaryMobile() == null || !isValidPhoneNumber(contactCreationRequestDTO.getPrimaryMobile())) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Primary mobile number cannot be null and must have a valid format"
            );
        }

        // Validate designation if present
        if (contactCreationRequestDTO.getDesignation() != null && contactCreationRequestDTO.getDesignation().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "Designation cannot be empty"
            );
        }

        // Validate addresses if present
        if (contactCreationRequestDTO.getAddresses() == null || contactCreationRequestDTO.getAddresses().isEmpty()) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "At least one address is required."
            );
        }

        if (contactCreationRequestDTO.getAddresses().size() > 2) {
            throw new ValidationFailedException(
                    "ValidationError",
                    "A maximum of 2 addresses is allowed."
            );
        }

        // Validate each address
        for (UserAddressDTO address : contactCreationRequestDTO.getAddresses()) {
            validateAddress(address);
        }

        return true;
    }
}
