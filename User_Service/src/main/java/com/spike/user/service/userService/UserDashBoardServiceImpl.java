package com.spike.user.service.userService;



import com.spike.user.dto.UserDashboardDTO;
import com.spike.user.entity.User;
import com.spike.user.helper.UserHelper;
import com.spike.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDashBoardServiceImpl implements UserDashBoardService {

    @Autowired
    private UserHelper userDashboardHelper ;

    @Autowired
    private UserRepository userRepository;

    //this service will return list of all users with filtration
    @Override
    public List<UserDashboardDTO> getUserFilteredDashboard(String name, String email, Date joiningDate, Double salary, int page, int size, String sort) {
        Specification<User> specs = Specification.where(userDashboardHelper.filterByName(name))
                .and(userDashboardHelper.filterByEmail(email))
                .and(userDashboardHelper.filterByJoiningDate(joiningDate))
                .and(userDashboardHelper.filterBySalary(salary));

        //created PageRequest for pagination and sorting
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort.split(",")[0]).with(Sort.Direction.fromString(sort.split(",")[1]))));

        //fetched filtered , paginated , sorted users
        Page<User> user = userRepository.findAll(specs, pageRequest);

        //convert to user dashboard dto
        return user.stream().map(this::userToUserDashboardDto).collect(Collectors.toList());

    }


    //this service will return the list of all users and set it in userDashboardDto for user dashboard
    @Override
    public List<UserDashboardDTO> getUsersForDashboard(int pagesize, int pageno, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        PageRequest pageRequest = PageRequest.of(pageno, pagesize, Sort.by(direction, sortParams[0]));
        Page<User> users = userRepository.findAll(pageRequest);
        return users.stream().map(this::userToUserDashboardDto).collect(Collectors.toList());

    }
    //this method will set data of user in userDashboardDto
    private UserDashboardDTO userToUserDashboardDto(User user) {
        UserDashboardDTO userDashboardDTO = new UserDashboardDTO();
        userDashboardDTO.setName(user.getName() != null ? user.getName() : null);
        userDashboardDTO.setDesignation(user.getDesignation() != null ? user.getDesignation() : null);
        userDashboardDTO.setEmail(user.getEmail() != null ? user.getEmail() : null);
        userDashboardDTO.setPrimaryMobile(user.getPrimaryMobileNumber() != null ? user.getPrimaryMobileNumber() : null);
        userDashboardDTO.setJoiningDate(user.getJoiningDate() != null ? user.getJoiningDate() : null);
        userDashboardDTO.setSalary(user.getSalary() != null ? user.getSalary() : null);
        //convert image into base64
        if (user.getProfilePicture() != null) {
            String filePath = user.getProfilePicture().getFilePath();
            File file = new File(filePath);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] fileData = fileInputStream.readAllBytes();
                String picture = Base64.getEncoder().encodeToString(fileData);
                userDashboardDTO.setProfilePicture(picture);

            } catch (IOException e) {
                userDashboardDTO.setProfilePicture(null);
            }
        } else {
            userDashboardDTO.setProfilePicture(null);
        }

        return userDashboardDTO;
    }
}

