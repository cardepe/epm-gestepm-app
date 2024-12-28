package com.epm.gestepm.modelapi.interventionshare.service;

import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;

import java.util.List;

public interface InterventionShareService {

	List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	
	/* Get all projects for Supervisor Tecnico */
	Long getAllShareTableCount(Long id, String type, Long projectId, Integer progress, Long userId);
	List<ShareTableDTO> getAllShareTable(Integer pageNumber, Integer pageSize, Long id, String type, Long projectId, Integer progress, Long userId);
}
