package com.epm.gestepm.model.interventionshare.dao;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;

import java.util.Date;
import java.util.List;

public interface InterventionShareRepositoryCustom {
	List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<InterventionShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	
	Long findAllShareTableCount(Long id, String type, Long projectId, Integer progress, Long userId);
	List<ShareTableDTO> findAllShareTable(Integer pageNumber, Integer pageSize, Long id, String type, Long projectId, Integer progress, Long userId);
}
