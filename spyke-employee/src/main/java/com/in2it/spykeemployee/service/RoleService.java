package com.in2it.spykeemployee.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.in2it.spykeemployee.entity.Role;


public interface RoleService {
	
	public Role createRole(String roleName, Set<String> permissions);
	Role createRole(String name);
    Optional<Role> getRoleById(int id);
    Optional<Role> getRoleByName(String name);
    List<Role> getAllRoles();
    void deleteRole(int id);
    void addPermissionToRole(int roleId, int permissionId);
    void removePermissionFromRole(int roleId, int permissionId);

}
