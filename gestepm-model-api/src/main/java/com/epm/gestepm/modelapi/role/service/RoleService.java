package com.epm.gestepm.modelapi.role.service;

import com.epm.gestepm.modelapi.role.dto.Role;

import java.util.List;

public interface RoleService {
	Role getRoleById(long roleId);
	List<Role> getAll();
}
