package com.spike.user.database;

import com.spike.user.entity.Role;
import com.spike.user.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
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
    @PostConstruct
    public void seedRoles() {
        System.out.println("Started RoleSeeder");

        List<String> roleNames = Arrays.asList("ADMIN", "MANAGER", "EMPLOYEE");

        for (String roleName : roleNames) {
            roleRepository.findByName(roleName).ifPresentOrElse(
                    existingRole -> System.out.println(roleName + " already exists."),
                    () -> {
                        Role newRole = new Role();
                        newRole.setName(roleName);
                        roleRepository.save(newRole);
                        System.out.println(roleName + " role created.");
                    }
            );
        }
    }
}