package com.in2it.spykeemployee.service;

import java.util.List;
import java.util.Optional;

import com.in2it.spykeemployee.entity.Permission;



public interface PermissionService {
	
    Permission createPermission(String permission);
    Optional<Permission> getPermissionById(int id);
    Optional<Permission> getPermissionByName(String permission);
    List<Permission> getAllPermissions();
    void deletePermission(int id);

}
