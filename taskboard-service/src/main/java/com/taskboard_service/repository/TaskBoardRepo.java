package com.taskboard_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskboard_service.entity.Taskboard;

public interface TaskBoardRepo extends JpaRepository<Taskboard, UUID>{

	List<Taskboard> findByDepartmentId(long departmentId);
	List<Taskboard> findByDepartmentName(String departmentName);
	
}
