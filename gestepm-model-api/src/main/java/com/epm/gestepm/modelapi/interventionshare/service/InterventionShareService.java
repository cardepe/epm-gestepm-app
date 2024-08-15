package com.epm.gestepm.modelapi.interventionshare.service;

import java.util.List;
import java.util.Locale;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionNoPrDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShareTableDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.User;

public interface InterventionShareService {
	
	InterventionShare getInterventionShareById(Long id);
	InterventionShare save(InterventionShare interventionShare);
	InterventionShare update(InterventionShare share, InterventionNoPrDTO interventionNoPrDTO, User user, String ip, Locale locale) throws Exception;
	void deleteById(Long id);
	Long getInterventionSharesCountByUser(Long userId);
	List<InterventionShareTableDTO> getInterventionSharesByUserDataTables(Long userId, PaginationCriteria pagination);
	List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> getShareTableByUserId(Long userId, Long projectId, Integer progress);
	Long getInterventionsCount(Long id);
	
	/* Get all projects for Supervisor Tecnico */
	Long getAllShareTableCount(Long id, String type, Long projectId, Integer progress, Long userId);
	List<ShareTableDTO> getAllShareTable(Integer pageNumber, Integer pageSize, Long id, String type, Long projectId, Integer progress, Long userId);
}
