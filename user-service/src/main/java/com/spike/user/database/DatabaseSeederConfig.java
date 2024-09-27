package com.spike.user.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeederConfig {

    private final RoleSeeder roleSeeder;
    private final DepartmentSeeder departmentSeeder;
    private final UserSeeder userSeeder;
    private final UserSeeder2 userSeeder2;
    private final UserSeeder3 userSeeder3;

    public DatabaseSeederConfig(RoleSeeder roleSeeder, DepartmentSeeder departmentSeeder, UserSeeder userSeeder, UserSeeder2 userSeeder2, UserSeeder3 userSeeder3) {
        this.roleSeeder = roleSeeder;
        this.departmentSeeder = departmentSeeder;
        this.userSeeder = userSeeder;
        this.userSeeder2 = userSeeder2;
        this.userSeeder3 = userSeeder3;
    }

    @Bean
    public CommandLineRunner databaseSeederRunner() {
        return args -> {
            roleSeeder.seedRoles();        // Run RoleSeeder first
            departmentSeeder.seedDepartments(); // Run DepartmentSeeder next
            userSeeder.seedAdminUser();   // Run UserSeeder last
            userSeeder2.seedAdminUser();   // Run UserSeeder last
            userSeeder3.seedAdminUser();   // Run UserSeeder last
        };
    }
}