package com.spike.user.service.userService;

import com.spike.user.dto.UserContactsDTO;

import java.util.List;

public interface UserContactService {


    List<UserContactsDTO> getUserContacts(String name, int pageno, int pagesize, String sort);

    List<UserContactsDTO> getAllUsersContact(int pagesize, int pageno, String sort);
}
