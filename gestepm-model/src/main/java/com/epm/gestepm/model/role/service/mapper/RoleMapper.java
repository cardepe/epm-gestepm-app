package com.epm.gestepm.model.role.service.mapper;

import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;

public class RoleMapper {

	private RoleMapper() {
		
	}

	public static SubRole mapDTOToSubRole(RoleDTO roleDTO) {
		
		SubRole subRole = new SubRole();
		
		subRole.setRol(roleDTO.getName());
		subRole.setPrice(roleDTO.getPrice());
		
		return subRole;
	}
	
}
