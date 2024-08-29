package com.in2it.spykeemployee.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.in2it.spykeemployee.entity.Employee;
import com.in2it.spykeemployee.entity.Permission;
import com.in2it.spykeemployee.entity.Role;
import com.in2it.spykeemployee.repository.EmployeeRepository;
import com.in2it.spykeemployee.repository.PermissionRepository;
import com.in2it.spykeemployee.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class DataInitializer {

    @Bean
    CommandLineRunner init(EmployeeRepository employeeRepository, RoleRepository roleRepository,
                           PermissionRepository permissionRepository) {
        return args -> {
            // Create and save roles
            Role superAdminRole = createAndSaveRole(roleRepository, "SUPER_ADMIN");
            Role adminRole = createAndSaveRole(roleRepository, "ADMIN");
            Role employeeRole = createAndSaveRole(roleRepository, "EMPLOYEE");
            Role traineeRole = createAndSaveRole(roleRepository, "TRAINEE");
            Role newJoineeRole = createAndSaveRole(roleRepository, "NEW_JOINEE");

            // Create and save permissions
            Permission readPermission = createAndSavePermission(permissionRepository, "READ_PRIVILEGES");
            Permission writePermission = createAndSavePermission(permissionRepository, "WRITE_PRIVILEGES");
            Permission updatePermission = createAndSavePermission(permissionRepository, "UPDATE_PRIVILEGES");
            Permission deletePermission = createAndSavePermission(permissionRepository, "DELETE_PRIVILEGES");
            Permission updateProfilePermission = createAndSavePermission(permissionRepository, "UPDATE_PROFILE_PRIVILEGES");

            // Assign permissions to roles
            superAdminRole.setPermissions(Set.of(readPermission, writePermission, updatePermission, deletePermission));
            roleRepository.save(superAdminRole);

            adminRole.setPermissions(Set.of(readPermission, writePermission, updatePermission));
            roleRepository.save(adminRole);
            
            employeeRole.setPermissions(Set.of(readPermission,updateProfilePermission));
            roleRepository.save(employeeRole);
            
            
            traineeRole.setPermissions(Set.of(readPermission,updateProfilePermission));
            roleRepository.save(traineeRole);
            
            traineeRole.setPermissions(Set.of(readPermission,updateProfilePermission));
            roleRepository.save(traineeRole);
            
            newJoineeRole.setPermissions(Set.of(readPermission));
            roleRepository.save(newJoineeRole);

            // Create and save employees
            createAndSaveEmployee(employeeRepository, "superadmin", "superadmin", superAdminRole);
            createAndSaveEmployee(employeeRepository, "admin", "admin", adminRole);
        };
    }

    private Role createAndSaveRole(RoleRepository roleRepository, String roleName) {
        return roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
            log.debug("Role {} created", roleName);
            return role;
        });
    }

    private Permission createAndSavePermission(PermissionRepository permissionRepository, String permissionName) {
        return permissionRepository.findByPermission(permissionName).orElseGet(() -> {
            Permission permission = new Permission();
            permission.setPermission(permissionName);
            permissionRepository.save(permission);
            log.debug("Permission {} created", permissionName);
            return permission;
        });
    }

    private void createAndSaveEmployee(EmployeeRepository employeeRepository, String username, String password, Role role) {
        if (employeeRepository.findByUsername(username).isEmpty()) {
            Employee employee = new Employee();
            employee.setFirstName(username);
            employee.setLastName(username);
            employee.setUsername(username);
//            employee.setEmail(username+"@gmail.com");
            employee.setPassword(new BCryptPasswordEncoder().encode(password));
            employee.setRoles(Set.of(role)); // Ensure that 'role' is saved and not transient
            employeeRepository.save(employee);
            log.debug("Employee {} created with role {}", username, role.getName());
        } else {
            log.debug("Employee {} already exists", username);
        }
    }
}