package com.spike.user.customMapper;

import com.spike.user.dto.DepartmentCreationDTO;
import com.spike.user.dto.DepartmentDropdownDTO;
import com.spike.user.dto.DepartmentResponseDTO;
import com.spike.user.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    // Mapping from DTO to Entity for creation
    Department departmentCreationDtoToEntity(DepartmentCreationDTO departmentCreationDTO);

    // Mapping from Entity to DTO for response
    DepartmentResponseDTO entityToDepartmentResponseDTO(Department department);

    // Mapping from Entity to Dropdown DTO
    DepartmentDropdownDTO entityToDepartmentDropdownDTO(Department department);

    // List mapping for Dropdown DTOs
    List<DepartmentDropdownDTO> entityListToDropdownDTOList(List<Department> departments);
}
