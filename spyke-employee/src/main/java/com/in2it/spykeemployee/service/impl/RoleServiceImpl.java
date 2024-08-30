package com.in2it.spykeemployee.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in2it.spykeemployee.entity.Permission;
import com.in2it.spykeemployee.entity.Role;
import com.in2it.spykeemployee.repository.PermissionRepository;
import com.in2it.spykeemployee.repository.RoleRepository;
import com.in2it.spykeemployee.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Role createRole(String roleName, Set<String> permissions) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Role createRole(String name) {
		Role newRole = new Role();
		newRole.setName("ROLE_"+name);
		return roleRepository.save(newRole);
	}

	@Override
	public Optional<Role> getRoleById(int id) {
		return roleRepository.findById(id);
	}

	@Override
	public Optional<Role> getRoleByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public void deleteRole(int id) {
		roleRepository.deleteById(id);
	}

	@Override
	public void addPermissionToRole(int roleId, int permissionId) {
		Optional<Role> roleOptional = roleRepository.findById(roleId);
		Optional<Permission> permissionOptional = permissionRepository.findById(permissionId);

		if (roleOptional.isPresent() && permissionOptional.isPresent()) {
			Role role = roleOptional.get();
			Permission permission = permissionOptional.get();
			role.getPermissions().add(permission);
			roleRepository.save(role);
		}
	}

	@Override
	public void removePermissionFromRole(int roleId, int permissionId) {
		Optional<Role> roleOptional = roleRepository.findById(roleId);
		Optional<Permission> permissionOptional = permissionRepository.findById(permissionId);

		if (roleOptional.isPresent() && permissionOptional.isPresent()) {
			Role role = roleOptional.get();
			Permission permission = permissionOptional.get();
			role.getPermissions().remove(permission);
			roleRepository.save(role);
		}
	}

}
