package com.epm.gestepm.modelapi.subfamily.service;

import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamilyDTO;

import java.util.List;

public interface SubFamilyService {

	SubFamily getById(Long id);
	List<RoleDTO> getSubRolsById(Long id);
	List<SubFamilyDTO> getByFamily(Long familyId);
	List<SubFamily> findAll();
	SubFamily save(SubFamily subFamily);
	void delete(Long subFamilyId);
}
