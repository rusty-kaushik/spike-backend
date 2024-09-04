package com.spike.user.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeederConfig {

    private final RoleSeeder roleSeeder;
    private final DepartmentSeeder departmentSeeder;
    private final UserSeeder userSeeder;

    public DatabaseSeederConfig(RoleSeeder roleSeeder, DepartmentSeeder departmentSeeder, UserSeeder userSeeder) {
        this.roleSeeder = roleSeeder;
        this.departmentSeeder = departmentSeeder;
        this.userSeeder = userSeeder;
    }

    @Bean
    public CommandLineRunner databaseSeederRunner() {
        return args -> {
            roleSeeder.seedRoles();        // Run RoleSeeder first
            departmentSeeder.seedDepartments(); // Run DepartmentSeeder next
            userSeeder.seedAdminUser();   // Run UserSeeder last
        };
    }
}