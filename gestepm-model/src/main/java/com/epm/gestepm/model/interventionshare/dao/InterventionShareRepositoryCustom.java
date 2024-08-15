package com.epm.gestepm.model.interventionshare.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShareTableDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;

import java.util.Date;
import java.util.List;

public interface InterventionShareRepositoryCustom {
	Long findInterventionSharesCountByUserId(Long userId);
	List<InterventionShareTableDTO> findInterventionSharesByUserDataTables(Long userId, PaginationCriteria pagination);
	List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> findShareTableByUserId(Long userId, Long projectId, Integer progress);
	List<InterventionShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<DailyPersonalSigningDTO> findDailyInterventionShareDTOByUserIdAndYear(Long userId, int year);
	Long findInterventionsCount(Long id);
	
	/* Get all projects for Supervisor Tecnico */
	Long findAllShareTableCount(Long id, String type, Long projectId, Integer progress, Long userId);
	List<ShareTableDTO> findAllShareTable(Integer pageNumber, Integer pageSize, Long id, String type, Long projectId, Integer progress, Long userId);
}
