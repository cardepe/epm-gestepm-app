package com.epm.gestepm.model.displacement.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementDTO;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementTableDTO;

import java.util.List;

public interface DisplacementRepositoryCustom {
	List<DisplacementTableDTO> findDisplacementsDataTables(PaginationCriteria pagination);
	Long findDisplacementsCount();
	List<DisplacementTableDTO> findDisplacementsDataTablesByProjectId(Long projectId, PaginationCriteria pagination);
	Long findDisplacementsCountByProjectId(Long projectId);
	List<DisplacementDTO> findNotDisplacementDTOsByProjectId(Long projectId);
	List<DisplacementDTO> findDisplacementDTOsByProjectId(Long projectId);
	void createProjectDisplacement(Long projectId, Long displacementId);
	void deleteProjectDisplacement(Long projectId, Long displacementId);
}
