package com.epm.gestepm.model.workshare.dao;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareTableDTO;

public interface WorkShareRepositoryCustom {

	List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> findShareTableByUserId(Long userId, Long projectId, Integer progress);
	Long findWorkSharesCountByUserId(Long userId);
	List<ShareTableDTO> findShareTableByProjectId(Long projectId);
	List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId);
	List<WorkShareTableDTO> findWorkSharesByUserDataTables(Long userId, PaginationCriteria pagination);
	List<WorkShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<WorkShare> findWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId);
	List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);

}
