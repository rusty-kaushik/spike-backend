package com.spike.user.service.userService;


import com.spike.user.dto.UserAddressDTO;
import com.spike.user.dto.UserContactsDTO;
import com.spike.user.entity.User;
import com.spike.user.entity.UserProfilePicture;
import com.spike.user.exceptions.EmployeeNotFoundException;
import com.spike.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserContactServiceImpl implements UserContactService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;


    public List<UserContactsDTO> getUserContacts(String name, int pageno, int pagesize, String sort) {


        String[] sortParams = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        PageRequest pageRequest = PageRequest.of(pageno, pagesize, Sort.by(direction, sortParams[0]));
        List<User> user = userRepository.findAllByName(name, pageRequest);
        if (user.isEmpty()) {
            throw new EmployeeNotFoundException("list is empty,no user found with the given name: " + name);
        } else {
            return user.stream().map(this::userToUserContacsDto).collect(Collectors.toList());
        }


    }

    @Override
    public List<UserContactsDTO> getAllUsersContact(int pagesize, int pageno, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        PageRequest pageRequest = PageRequest.of(pageno, pagesize, Sort.by(direction, sortParams[0]));
        Page<User> user = userRepository.findAll(pageRequest);
        if (user.isEmpty()) {
            throw new EmployeeNotFoundException("list is empty , no user exist");
        } else {
            return user.stream().map(this::userToUserContacsDto).collect(Collectors.toList());

        }
    }


    private UserContactsDTO userToUserContacsDto(User user) {
        UserContactsDTO userContactsDto = new UserContactsDTO();
        userContactsDto.setName(user.getName() != null ? user.getName() : null);
        userContactsDto.setDesignation(user.getDesignation() != null ? user.getDesignation() : null);
        userContactsDto.setPrimaryMobile(user.getPrimaryMobileNumber() != null ? user.getPrimaryMobileNumber() : null);
        // Filter and set primary address

        List<UserAddressDTO> primaryAddress = user.getAddresses().stream()
                .filter(address -> "PRIMARY".equals(address.getType()))
                .map(address -> mapper.map(address, UserAddressDTO.class))
                .collect(Collectors.toList());

        userContactsDto.setPrimaryAddress(primaryAddress);
        userContactsDto.setInstagramUrl(user.getUserSocials() != null ? user.getUserSocials().getInstagramUrl() : null);
        userContactsDto.setFacebookUrl(user.getUserSocials() != null ? user.getUserSocials().getFacebookUrl() : null);
        userContactsDto.setLinkedinUrl(user.getUserSocials() != null ? user.getUserSocials().getLinkedinUrl() : null);

        //convert profile picture into base64
        UserProfilePicture profilePicture = user.getProfilePicture();
        if (profilePicture != null) {
            try {
                File file = new File(profilePicture.getFilePath());
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = fileInputStream.readAllBytes();
                String picture = Base64.getEncoder().encodeToString(bytes);
                userContactsDto.setProfilePicture(picture);

            } catch (IOException e) {
                userContactsDto.setProfilePicture(null);
            }
        } else {
            userContactsDto.setProfilePicture(null);
        }
        return userContactsDto;
    }
}








