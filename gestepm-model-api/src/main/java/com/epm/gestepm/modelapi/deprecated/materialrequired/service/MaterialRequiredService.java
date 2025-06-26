package com.epm.gestepm.modelapi.deprecated.materialrequired.service;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequired;
import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequiredDTO;
import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequiredTableDTO;

public interface MaterialRequiredService {

	MaterialRequired getById(Long id);
	List<MaterialRequired> findAll();
	MaterialRequired save(MaterialRequired materialRequired);
	void delete(Long materialRequiredId);
	
	List<MaterialRequiredTableDTO> getMaterialsRequiredDataTablesByProjectId(Long projectId, PaginationCriteria pagination);
	Long getMaterialsRequiredCountByProjectId(Long projectId);
	
	List<MaterialRequiredDTO> getMaterialsRequiredByProjectId(Long projectId);
}
