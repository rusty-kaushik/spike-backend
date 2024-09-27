package com.spike.user.service.departmentService;

import com.spike.user.dto.DepartmentCreationDTO;
import com.spike.user.dto.DepartmentDropdownDTO;
import com.spike.user.dto.DepartmentResponseDTO;
import com.spike.user.entity.Department;
import com.spike.user.exceptions.DepartmentNotFoundException;

import java.util.List;

public interface DepartmentService {

    // Add methods for department operations here

    /**
     * Creates a new department.
     *
     * @param department The department entity to be created.
     * @return The created department entity.
     */
    DepartmentResponseDTO createDepartment(DepartmentCreationDTO department);

    /**
     * Retrieves all departments.
     *
     * @return A list of all department entities.
     */
    List<DepartmentDropdownDTO> getAllDepartments();

    /**
     * Retrieves a department by its ID.
     *
     * @param id The ID of the department to retrieve.
     * @return The department entity with the given ID.
     * @throws DepartmentNotFoundException if no department with the given ID exists.
     */
    DepartmentResponseDTO getDepartmentById(Long id);

    /**
     * Retrieves a department by its Name.
     *
     * @param name The name of the department to retrieve.
     * @return The department entity with the given name.
     * @throws DepartmentNotFoundException if no department with the given name exists.
     */
    DepartmentResponseDTO getDepartmentByName(String name);

    /**
     * Updates an existing department.
     *
     * @param id The ID of the department to be updated.
     * @param department The updated department entity.
     * @return The updated department entity.
     * @throws DepartmentNotFoundException if no department with the given ID exists.
     */
    DepartmentResponseDTO updateDepartment(Long id, DepartmentCreationDTO department);

    /**
     * Deletes a department by its ID.
     *
     * @param id The ID of the department to be deleted.
     * @throws DepartmentNotFoundException if no department with the given ID exists.
     */
    void deleteDepartment(Long id);

    boolean checkDepartmentExistence(Long id);
}
