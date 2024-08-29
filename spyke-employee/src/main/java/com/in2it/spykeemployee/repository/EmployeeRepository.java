package com.in2it.spykeemployee.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in2it.spykeemployee.entity.Employee;
import java.util.List;




@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

	Optional<Employee> findByUsername(String username);
//	Optional<Employee> List<Employee> findByOrderByEmployeeId(String employeeId);(String email);
	
}
