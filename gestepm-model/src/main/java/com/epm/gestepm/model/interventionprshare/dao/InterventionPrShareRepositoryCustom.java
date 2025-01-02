package com.epm.gestepm.model.interventionprshare.dao;

import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface InterventionPrShareRepositoryCustom {

	List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> findShareTableByProjectId(Long projectId);
	List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId);
	List<InterventionPrShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
	List<InterventionPrShare> findWeekSigningsByProjectId(LocalDateTime startDate, LocalDateTime endDate, Long projectId);
	List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);
}
