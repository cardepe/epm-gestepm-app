package com.epm.gestepm.model.displacementshare.dao;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareTableDTO;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;

public interface DisplacementShareRepositoryCustom {

	Long findDisplacementSharesCountByUserId(Long userId);
	List<ShareTableDTO> findShareTableByProjectId(Long projectId);
	List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId);
	List<DisplacementShareTableDTO> findDisplacementSharesByUserDataTables(Long userId, PaginationCriteria pagination);
	List<DisplacementShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId, Integer manual);
	List<DailyPersonalSigningDTO> findDailyDisplacementShareDTOByUserIdAndYear(Long userId, int year);
	List<ExpensesMonthDTO> findTimeMonthDTOByProjectId(Long projectId, Integer year);

}
