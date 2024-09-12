package com.spike.user.repository;

import com.spike.user.dto.DepartmentDropdownDTO;
import com.spike.user.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);

    @Query("SELECT new com.spike.user.dto.DepartmentDropdownDTO(d.id, d.name) FROM Department d")
    List<DepartmentDropdownDTO> findAllDepartmentDTOs();
}
