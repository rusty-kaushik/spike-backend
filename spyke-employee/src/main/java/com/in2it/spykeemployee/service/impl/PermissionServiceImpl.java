package com.in2it.spykeemployee.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in2it.spykeemployee.entity.Permission;
import com.in2it.spykeemployee.repository.PermissionRepository;
import com.in2it.spykeemployee.service.PermissionService;


@Service
public class PermissionServiceImpl implements PermissionService{


	
	@Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(String permission) {
        Permission newPermission = new Permission();
        newPermission.setPermission(permission);
        return permissionRepository.save(newPermission);
    }

    @Override
    public Optional<Permission> getPermissionById(int id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Optional<Permission> getPermissionByName(String permission) {
        return permissionRepository.findByPermission(permission);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public void deletePermission(int id) {
        permissionRepository.deleteById(id);
    }

}
