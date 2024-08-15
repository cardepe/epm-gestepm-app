package com.epm.gestepm.model.interventionprshare.dao;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;

public interface InterventionPrShareRepositoryCustom {

	List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> findShareTableByUserId(Long userId, Long projectId, Integer progress);
	List<ShareTableDTO> findShareTableByProjectId(Long projectId);
	List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId);
	List<InterventionPrShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<InterventionPrShare> findWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId);
	List<DailyPersonalSigningDTO> findDailyInterventionPrShareDTOByUserIdAndYear(Long userId, int year);
	List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);
}
