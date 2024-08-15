package com.epm.gestepm.model.role.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.role.dto.Role;

public interface RoleRepository extends CrudRepository<Role, Long>, RoleRepositoryCustom {
	
}
