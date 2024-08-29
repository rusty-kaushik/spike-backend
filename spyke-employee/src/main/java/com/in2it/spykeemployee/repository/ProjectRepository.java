package com.in2it.spykeemployee.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in2it.spykeemployee.entity.Project;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
	
	Optional<Project> findByName(String name);

}
