package com.epm.gestepm.model.subrole.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.subrole.dto.SubRole;

public interface SubRoleRepository extends CrudRepository<SubRole, Long>, SubRoleRepositoryCustom {
	SubRole findByRol(String rol);
}
