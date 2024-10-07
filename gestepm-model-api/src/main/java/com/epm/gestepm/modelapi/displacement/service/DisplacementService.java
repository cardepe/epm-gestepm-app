package com.epm.gestepm.modelapi.displacement.service;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementDTO;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementTableDTO;

public interface DisplacementService {
	Displacement getDisplacementById(long displacementId);
	List<Displacement> getAll();
	void save(Displacement displacement);
	void delete(Long id);
	List<DisplacementTableDTO> getDisplacementsDataTablesByProjectId(Long projectId, PaginationCriteria pagination);
	Long getDisplacementsCountByProjectId(Long projectId);
	List<DisplacementDTO> getNotDisplacementDTOsByProjectId(Long projectId);
	List<DisplacementDTO> getDisplacementDTOsByProjectId(Long projectId);
	void createProjectDisplacement(Long projectId, Long displacementId);
	void deleteProjectDisplacement(Long projectId, Long displacementId);
}
