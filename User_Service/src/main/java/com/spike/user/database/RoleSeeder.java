package com.spike.user.database;

import com.spike.user.entity.Role;
import com.spike.user.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

//* The RoleSeeder class populates the role database at application startup.
//* It checks if certain roles (like "SUPER_ADMIN", "ADMIN", etc.) exist in the repository.
//* If any roles are missing, it creates and saves them. This ensures that essential roles are preloaded into the database.
@Component
public class RoleSeeder {

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(RoleSeeder.class);

    @PostConstruct
    public void seedRoles() {
        System.out.println("Started RoleSeeder");

        // Iterate over enum values
        Arrays.stream(RoleType.values()).forEach(roleType -> {
            String roleName = roleType.name();

            roleRepository.findByName(roleName).ifPresentOrElse(
                    existingRole -> logger.info("{} already exists.", roleName),
                    () -> {
                        Role newRole = new Role();
                        newRole.setName(roleName);
                        roleRepository.save(newRole);
                        logger.info("{} role created.", roleName);
                    }
            );
        });
    }
}