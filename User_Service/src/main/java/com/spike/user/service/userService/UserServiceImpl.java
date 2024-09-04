package com.spike.user.service.userService;

import com.spike.user.dto.UserAddressDTO;
import com.spike.user.dto.UserChangePasswordDTO;
import com.spike.user.dto.UserCreationRequestDTO;
import com.spike.user.entity.User;
import com.spike.user.entity.UserAddress;
import com.spike.user.exceptions.EmployeeNotFoundException;
import com.spike.user.exceptions.PasswordNotMatchException;
import com.spike.user.exceptions.UnexpectedException;
import com.spike.user.helper.UserHelper;
import com.spike.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User createNewUser(MultipartFile profilePicture, UserCreationRequestDTO userRequest) {
        try {
            // SETS USER
            User user = userHelper.dtoToEntityForUserMaster(userRequest);
            // SETS ADDRESSES
            for (UserAddressDTO addressDTO : userRequest.getAddresses()) {
                UserAddress userAddress = userHelper.dtoToEntityForUserAddress(addressDTO);
                user.addAddress(userAddress);
            }
            //SETS SOCIAL URL
            user.addSocial(userHelper.dtoToEntityForUserSocials(userRequest));
            //SETS PROFILE PICTURE
            user.addPicture(userHelper.dtoToEntityForUserPicture(profilePicture, userRequest));

            return userRepository.save(user);
        } catch ( Exception e ) {
            throw new RuntimeException("Error creating user", e.getCause());
        }

    }

    @Override
    public List<User> getAllUsers() {
        try{
            return userRepository.findAll();
        } catch ( Exception e ) {
            throw new RuntimeException("Error fetching user", e.getCause());
        }
    }

    @Override
    public String updateSelfPassword(String username, UserChangePasswordDTO userChangePasswordDTO) {
        try{
            User existingUser = userRepository.findByUsername(username);
            if (existingUser == null) {
                throw new EmployeeNotFoundException("User not found");
            }
            if (!passwordEncoder.matches(userChangePasswordDTO.getOldPassword(), existingUser.getPassword())) {
                throw new PasswordNotMatchException("Old password does not match");
            }
            existingUser.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
            userRepository.save(existingUser);
            return "Successfully updated password for " + username;
        } catch (EmployeeNotFoundException | PasswordNotMatchException e) {
            throw e;
        } catch ( Exception e ) {
            throw new UnexpectedException("An unexpected error occurred while updating the password", e);
        }
    }
}