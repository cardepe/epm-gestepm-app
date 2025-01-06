package com.epm.gestepm.model.displacementshare.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareTableDTO;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface DisplacementShareRepositoryCustom {

	Long findDisplacementSharesCountByUserId(Long userId);
	List<ShareTableDTO> findShareTableByProjectId(Long projectId);
	List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId);
	List<DisplacementShareTableDTO> findDisplacementSharesByUserDataTables(Long userId, PaginationCriteria pagination);
	List<DisplacementShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId, Integer manual);
	List<ExpensesMonthDTO> findTimeMonthDTOByProjectId(Long projectId, Integer year);

}
