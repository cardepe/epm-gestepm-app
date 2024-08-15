package com.epm.gestepm.model.subfamily.dao;

import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamilyDTO;

import java.util.List;

public interface SubFamilyRepositoryCustom {
	public List<SubFamilyDTO> findByFamily(Long familyId);
	public List<RoleDTO> findSubRolsById(Long id);
}
