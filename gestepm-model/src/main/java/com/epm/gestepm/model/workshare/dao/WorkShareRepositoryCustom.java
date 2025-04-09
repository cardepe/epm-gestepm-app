package com.epm.gestepm.model.workshare.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareTableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkShareRepositoryCustom {

	List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	Long findWorkSharesCountByUserId(Long userId);
	List<ShareTableDTO> findShareTableByProjectId(Long projectId);
	List<WorkShareTableDTO> findWorkSharesByUserDataTables(Long userId, PaginationCriteria pagination);
	List<WorkShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
	List<WorkShare> findWeekSigningsByProjectId(LocalDateTime startDate, LocalDateTime endDate, Long projectId);
	List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);

}
