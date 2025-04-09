package com.epm.gestepm.modelapi.displacementshare.service;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareTableDTO;

import java.util.List;

public interface DisplacementShareService {
	
	DisplacementShare getDisplacementShareById(Long id);
	DisplacementShare save(DisplacementShare displacementShare);
	void deleteById(Long id);
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	Long getDisplacementSharesCountByUser(Long userId);
	List<DisplacementShareTableDTO> getDisplacementSharesByUserDataTables(Long userId, PaginationCriteria pagination);
}
