package com.spike.user.customMapper;

import com.spike.user.dto.*;
import com.spike.user.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

// Annotates the interface to indicate that it is a MapStruct mapper.
// The componentModel = "spring" makes it a Spring bean, allowing you to inject it into other Spring components.
@Mapper(componentModel = "spring")
public interface UserMapper {

    // Provides a default implementation instance for the mapper.
    // This is useful for standalone usage or testing.
    // In a Spring context, you generally rely on Spring to manage the mapper instance.
    // UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    // MAP USER DTO TO TABLE
    @Mapping(source = "employeeCode", target = "username") // Specifies that the employeeCode field in the DTO should be mapped to the username field in the User entity. Similar mappings are defined for other fields.
    @Mapping(target = "password", ignore = true) // Indicates that the password field should not be mapped automatically. This is useful when you need to handle the password field manually
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "departments", ignore = true)
    @Mapping(target = "userSocials", ignore = true)
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    User dtoToEntity(UserCreationRequestDTO userRequest);


    // MAP ADDRESS DTO TO TABLE
    UserAddress dtoToEntityAddress(UserAddressDTO userAddress);

    // MAP SOCIALS DTO TO TABLE
    UserSocials dtoToEntitySocials(UserCreationRequestDTO userRequest);

     //MAP USER TO USERCONTACTSDTO
     @Mapping(target = "profilePicture", ignore=true)
     UserContactsDTO entityToDtoContact(User user);

     //MAP USER TO USERDASHBOARDDTO
     @Mapping(target = "profilePicture", ignore = true)

    UserDashboardDTO entityToDtoDashboard(User user);

    UserAddressDTO entityToDtoAddress(UserAddress userAddress);

    DepartmentResponseDTO entityToDepartmentDtoResponse(Department department);

    Contacts contactDtoToEntity(ContactsDto contactsDto);

    ContactsDto entityToContactsDto(Contacts contacts);

    // Map UserFullUpdateDTO to User for updates
    @Mapping(target = "password", ignore = true) // Ignore password during update
    @Mapping(target = "role", ignore = true) // Ignore role if not updating
    @Mapping(target = "departments", ignore = true) // Ignore departments if not updating
    @Mapping(target = "userSocials", ignore = true) // Ignore socials if not updating
    @Mapping(target = "addresses", ignore = true) // Ignore addresses if handled separately
    User updateUserFromDTO(UserFullUpdateDTO dto, @MappingTarget User user);

    // Method to map social fields from DTO to User
    default void mapSocials(UserFullUpdateDTO dto, User user) {
        UserSocials userSocials = user.getUserSocials();
        if (userSocials == null) {
            userSocials = new UserSocials(); // Create if it doesn't exist
        }
        userSocials.setLinkedinUrl(dto.getLinkedinUrl());
        userSocials.setFacebookUrl(dto.getFacebookUrl());
        userSocials.setInstagramUrl(dto.getInstagramUrl());
        user.setUserSocials(userSocials); // Update the user with new socials
    }

}
