package com.epm.gestepm.model.subfamily.dao;

import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamilyOldDTO;

import java.util.List;

public interface SubFamilyRepositoryCustom {
	List<SubFamilyOldDTO> findByFamily(Long familyId);
	List<RoleDTO> findSubRolsById(Long id);
}
