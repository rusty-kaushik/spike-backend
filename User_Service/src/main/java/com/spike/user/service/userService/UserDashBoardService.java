package com.spike.user.service.userService;

import com.spike.user.dto.UserDashboardDTO;

import java.util.Date;
import java.util.List;

public interface UserDashBoardService {
    List<UserDashboardDTO> getUserFilteredDashboard(String name, String email, Date joiningDate, Double salary, int page, int size, String sort);

    List<UserDashboardDTO> getUsersForDashboard(int pagesize, int pageno, String sort);
}
