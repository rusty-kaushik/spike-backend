package com.spike.user.database;

import com.spike.user.entity.Role;
import com.spike.user.entity.User;
import com.spike.user.repository.RoleRepository;
import com.spike.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class UserSeeder {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void seedAdminUser() {
        System.out.println("Started UserSeeder");
        User user = userRepository.findByUsername("admin5");
        if (user == null) {
            System.out.println("Started to seed user_master");
            Role adminRole = roleRepository.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found"));
            User admin = new User();
            admin.setId(1L);
            admin.setBackupEmail("backup_admin@in2it.com");
            admin.setDesignation("ADMIN");
            admin.setEmail("admin@in2it.com");
            admin.setEmployeeCode("00000001");
            admin.setJoiningDate(new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime());
            admin.setManagerId(null);
            admin.setName("Admin");
            admin.setPassword(passwordEncoder.encode("in2it"));
            admin.setPostCreate(true);
            admin.setPrimaryMobileNumber("9958032167");
            admin.setSalary(0.0);
            admin.setSecondaryMobileNumber("9958032167");
            admin.setUsername("admin");
            admin.setProfilePicture(null);
            admin.setRole(adminRole);
            admin.setUserSocials(null);
            userRepository.save(admin);
            System.out.println("Admin created.");
        } else {
            System.out.println("Admin already exists.");
        }
    }
}