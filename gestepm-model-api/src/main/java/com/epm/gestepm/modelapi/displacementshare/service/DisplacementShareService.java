package com.epm.gestepm.modelapi.displacementshare.service;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareTableDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface DisplacementShareService {
	
	DisplacementShare getDisplacementShareById(Long id);
	DisplacementShare save(DisplacementShare displacementShare);
	void deleteById(Long id);
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);
	Long getDisplacementSharesCountByUser(Long userId);
	List<DisplacementShareTableDTO> getDisplacementSharesByUserDataTables(Long userId, PaginationCriteria pagination);
	List<DisplacementShare> getWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId, Integer manual);

}
