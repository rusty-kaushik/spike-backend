package com.spike.user.database;

import com.spike.user.entity.Role;
import com.spike.user.entity.User;
import com.spike.user.exceptions.RoleNotFoundException;
import com.spike.user.exceptions.UnexpectedException;
import com.spike.user.repository.RoleRepository;
import com.spike.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class UserSeeder {

    private static final Logger logger = LoggerFactory.getLogger(UserSeeder.class);

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void seedAdminUser() {
        try {
            logger.info("Started UserSeeder");
            User user = userRepository.findByUsername("backend_admin");
            if (user == null) {
                logger.info("Started to seed user_master");
                Role adminRole = roleRepository.findById(1L)
                        .orElseThrow(() -> new RoleNotFoundException("ValidationError","Role not found"));
                User admin = new User();
                admin.setId(1L);
                admin.setBackupEmail("backup_backend_admin@in2it.com");
                admin.setDesignation("ADMIN");
                admin.setEmail("backend_admin@in2it.com");
                admin.setEmployeeCode("IN2IT1");
                admin.setJoiningDate(new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime());
                admin.setManagerId(null);
                admin.setName("Backend");
                admin.setPassword(passwordEncoder.encode("Z7g@1Lw9Qv#"));
                admin.setPostCreate(true);
                admin.setPrimaryMobileNumber("9958032167");
                admin.setSalary(0.0);
                admin.setSecondaryMobileNumber("9958032167");
                admin.setUsername("backend_admin");
                admin.setProfilePicture(null);
                admin.setRole(adminRole);
                admin.setUserSocials(null);
                userRepository.save(admin);
                logger.info("Admin created.");
            } else {
                logger.info("Admin already exists.");
            }
        } catch (RoleNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException("UnexpectedException","Admin cannot be created for unknown reason.");
        }
    }
}