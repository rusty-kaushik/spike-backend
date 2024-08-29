package com.in2it.spykeemployee.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in2it.spykeemployee.entity.Permission;



@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer>{
	
	Optional<Permission> findByPermission(String permission);

}
