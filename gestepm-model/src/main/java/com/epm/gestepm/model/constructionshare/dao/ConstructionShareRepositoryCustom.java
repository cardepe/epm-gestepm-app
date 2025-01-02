package com.epm.gestepm.model.constructionshare.dao;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ConstructionShareRepositoryCustom {

	List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> findShareTableByProjectId(Long projectId);
	List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId);
	List<ConstructionShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
	List<ConstructionShare> findWeekSigningsByProjectId(LocalDateTime startDate, LocalDateTime endDate, Long projectId);
}
