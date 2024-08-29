package com.in2it.spykeemployee.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.in2it.spykeemployee.entity.Employee;
import com.in2it.spykeemployee.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
public class EmployeeUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employeeOptional = employeeRepository.findByUsername(username);

        if (!employeeOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Employee employee = employeeOptional.get();

        return new org.springframework.security.core.userdetails.User(
                employee.getUsername(),
                employee.getPassword(),
                employee.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
//                        .flatMap(role -> role.getPermissions().stream())
//                        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                        .collect(Collectors.toList())
        );
//      return new org.springframework.security.core.userdetails.User(
//      employee.getUsername(),
//      employee.getPassword(),
//      employee.getRoles().stream().map(authority-> new SimpleGrantedAuthority(username))
//        

         
        
    }
}