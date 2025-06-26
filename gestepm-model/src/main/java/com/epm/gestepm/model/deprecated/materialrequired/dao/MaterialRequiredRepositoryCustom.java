package com.epm.gestepm.model.deprecated.materialrequired.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequiredDTO;
import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequiredTableDTO;

import java.util.List;

public interface MaterialRequiredRepositoryCustom {
	List<MaterialRequiredTableDTO> findMaterialsRequiredDataTablesByProjectId(Long projectId, PaginationCriteria pagination);
	Long findMaterialsRequiredCountByProjectId(Long projectId);
	
	List<MaterialRequiredDTO> findMaterialsRequiredByProjectId(Long projectId);
}
