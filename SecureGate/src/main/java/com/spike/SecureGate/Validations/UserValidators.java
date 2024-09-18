package com.spike.SecureGate.Validations;

import com.spike.SecureGate.DTO.userDto.*;
import com.spike.SecureGate.JdbcHelper.UserDbService;
import com.spike.SecureGate.enums.*;
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

        // Validate name (name cannot be null or empty, and no special characters allowed)
        if (userRequest.getName() == null || userRequest.getName().isEmpty() || !userRequest.getName().matches("^[a-zA-Z0-9 ]*$")) {
            throw new IllegalArgumentException("Name cannot be null, empty, or contain special characters");
        }

        // Validate email (email cannot be null and must be valid)
        if (userRequest.getEmail() == null || !isValidEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException("Email cannot be null and must have a valid format");
        }

        // Validate employee code (employee code cannot be null or empty)
        if (userRequest.getEmployeeCode() == null || userRequest.getEmployeeCode().isEmpty()) {
            throw new IllegalArgumentException("Employee code cannot be null or empty");
        }

        // Validate role (role must be either 'MANAGER' or 'EMPLOYEE', cannot be null)
        if (userRequest.getRole() == null || (!userRequest.getRole().equals("MANAGER") && !userRequest.getRole().equals("EMPLOYEE"))) {
            throw new IllegalArgumentException("Role must be either 'MANAGER' or 'EMPLOYEE'");
        }

        // Validate primary mobile number (must not be null and must be valid)
        if (userRequest.getPrimaryMobileNumber() == null || !isValidPhoneNumber(userRequest.getPrimaryMobileNumber())) {
            throw new IllegalArgumentException("Primary mobile number cannot be null and must have a valid format");
        }

        // Validate joining date (must not be null and must be in the past)
        if (userRequest.getJoiningDate() == null || userRequest.getJoiningDate().after(new Date())) {
            throw new IllegalArgumentException("Joining date cannot be null and must be in the past");
        }

        // Validate salary (salary must not be null and must be non-negative)
        if (userRequest.getSalary() == null || userRequest.getSalary() < 0) {
            throw new IllegalArgumentException("Salary cannot be null or negative");
        }

        // Validate department (mandatory and must contain valid departments)
        List<String> validDepartments = userDbService.getAllDepartmentNames();
        if (userRequest.getDepartment() == null || userRequest.getDepartment().isEmpty()) {
            throw new IllegalArgumentException("Department is required and cannot be empty");
        } else {
            for (String department : userRequest.getDepartment()) {
                if (!validDepartments.contains(department)) {
                    throw new IllegalArgumentException("Invalid department: " + department);
                }
            }
        }

        // Validate optional fields

        // Validate backup email if present
        if (userRequest.getBackupEmail() != null && !isValidEmail(userRequest.getBackupEmail())) {
            throw new IllegalArgumentException("Invalid backup email format");
        }

        // Validate designation if present
        if (userRequest.getDesignation() != null && userRequest.getDesignation().isEmpty()) {
            throw new IllegalArgumentException("Designation cannot be empty");
        }

        // Validate manager ID if present (skip if null, must be positive if provided)
        if (userRequest.getManagerId() != null && userRequest.getManagerId() > 0) {
            throw new IllegalArgumentException("Manager ID must be positive");
        }

        // Validate secondary mobile number if present
        if (userRequest.getSecondaryMobileNumber() != null && !isValidPhoneNumber(userRequest.getSecondaryMobileNumber())) {
            throw new IllegalArgumentException("Invalid secondary mobile number format");
        }

        if (userRequest.getAddresses() == null || userRequest.getAddresses().isEmpty()) {
            throw new IllegalArgumentException("At least one address is required.");
        }

        if (userRequest.getAddresses().size() > 2) {
            throw new IllegalArgumentException("A maximum of 2 addresses is allowed.");
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
            throw new IllegalArgumentException("Address cannot be null");
        }

        // Validate Line1 (mandatory)
        if (address.getLine1() == null || address.getLine1().isEmpty()) {
            throw new IllegalArgumentException("Address Line1 cannot be null or empty");
        }

        // Validate State (mandatory and must be valid)
        if (address.getState() == null || address.getState().isEmpty()) {
            throw new IllegalArgumentException("State cannot be null or empty");
        }

        if (!Countries.isValidCountry(address.getCountry())) {
            throw new IllegalArgumentException("Invalid country: " + address.getCountry() + ". Please provide a valid country.");
        }

        if (address.getState() == null ||  address.getState().isEmpty()) {
            throw new IllegalArgumentException("State cannot be null or empty");
        }
        validateAddressState(address.getCountry(), address.getState());

        // Validate City (mandatory)
        if (address.getCity() == null || address.getCity().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }

        // Validate Zip Code (mandatory, must follow the format)
        if (address.getZip() == null ||  address.getZip().isEmpty()) {
            throw new IllegalArgumentException("ZIP cannot be null or empty");
        }
        validateZipCode(address.getCountry(), address.getZip() );

        // Validate Country (mandatory)
        if (address.getCountry() == null || address.getCountry().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }

        // Validate Address Type (mandatory, must be either 'PERMANENT' or 'CURRENT')
        if (address.getType() == null || (!address.getType().equals("PERMANENT") && !address.getType().equals("CURRENT"))) {
            throw new IllegalArgumentException("Address type must be either 'PERMANENT' or 'CURRENT'");
        }

        // Optional fields, validate only if provided

        // District (optional)
        if (address.getDistrict() != null && address.getDistrict().isEmpty()) {
            throw new IllegalArgumentException("District cannot be empty if provided");
        }
    }

    private void validateAddressState(String country, String state) {
        if(country == null || country.isEmpty()){
            throw new IllegalArgumentException("Country cannot be empty");
        }

        if (country.equals("UNITED_STATES")) {
            if (!UnitedStates.isValidState(state)) {
                throw new IllegalArgumentException("Invalid state: " + state + ". Please provide a valid state.");
            }
        } else if (country.equals("CHINA")) {
            if (!China.isValidRegion(state)) {
                throw new IllegalArgumentException("Invalid state: " + state + ". Please provide a valid state.");
            }
        } else if (country.equals("JAPAN")) {
            if (!Japan.isValidRegion(state)) {
                throw new IllegalArgumentException("Invalid state: " + state + ". Please provide a valid state.");
            }
        } else if (country.equals("GERMANY")) {
            if (!Germany.isValidRegion(state)) {
                throw new IllegalArgumentException("Invalid state: " + state + ". Please provide a valid state.");
            }
        } else if (country.equals("INDIA")) {
            if (!India.isValidRegion(state)) {
                throw new IllegalArgumentException("Invalid state: " + state + ". Please provide a valid state.");
            }
        } else if (country.equals("SOUTH_AFRICA")) {
            if (!SouthAfrica.isValidProvince(state)) {
                throw new IllegalArgumentException("Invalid state: " + state + ". Please provide a valid state.");
            }
        }
    }

    private void validateZipCode(String country, String zipCode) {
        if (country.equals("UNITED_STATES")){
            if (!java.util.regex.Pattern.matches("^(\\d{5})(-\\d{4})?$", zipCode)) {
                throw new IllegalArgumentException("Invalid ZIP code: " + zipCode + ". Please provide a valid ZIP code.");
            }
        } else if (country.equals("CHINA")) {
            if (!zipCode.matches("^\\d{6}$")) {
                throw new IllegalArgumentException("Invalid ZIP code: " + zipCode + ". Please provide a valid ZIP code for China.");
            }
        } else if (country.equals("JAPAN")) {
            if (!zipCode.matches("^\\d{3}-\\d{4}$")) {
                throw new IllegalArgumentException("Invalid ZIP code: " + zipCode + ". Please provide a valid ZIP code for Japan.");
            }
        } else if (country.equals("GERMANY")) {
            if (!zipCode.matches("^\\d{5}$")) {
                throw new IllegalArgumentException("Invalid ZIP code: " + zipCode + ". Please provide a valid ZIP code for Germany.");
            }
        } else if (country.equals("INDIA")) {
            if (!zipCode.matches("^\\d{6}$")) {
                throw new IllegalArgumentException("Invalid ZIP code: " + zipCode + ". Please provide a valid ZIP code for India.");
            }
        } else if (country.equals("SOUTH_AFRICA")) {
            if (!zipCode.matches("^\\d{4}$")) {
                throw new IllegalArgumentException("Invalid ZIP code: " + zipCode + ". Please provide a valid ZIP code for South Africa.");
            }
        }
    }



    private boolean isValidEmail(String email) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return Pattern.compile("^[0-9]{10}$").matcher(phoneNumber).matches(); // ("^\\+?[0-9]{10,15}$")
    }


    public boolean validateUserUpdatePassword(UserChangePasswordDTO userChangePasswordDTO) {
        if (userChangePasswordDTO.getNewPassword() != null && !userChangePasswordDTO.getNewPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,20}$")) {
            throw new IllegalArgumentException("Invalid password");
        }
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
