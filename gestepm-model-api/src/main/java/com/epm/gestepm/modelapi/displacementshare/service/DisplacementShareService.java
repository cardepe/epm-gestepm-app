package com.epm.gestepm.modelapi.displacementshare.service;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareTableDTO;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;

public interface DisplacementShareService {
	
	DisplacementShare getDisplacementShareById(Long id);
	DisplacementShare save(DisplacementShare displacementShare);
	void deleteById(Long id);
	List<ShareTableDTO> getShareTableByProjectId(Long projectId);
	List<ShareTableDTO> getShareTableByUserSigningId(Long userSigning);
	Long getDisplacementSharesCountByUser(Long userId);
	List<DisplacementShareTableDTO> getDisplacementSharesByUserDataTables(Long userId, PaginationCriteria pagination);
	List<DisplacementShare> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId, Integer manual);
	List<DailyPersonalSigningDTO> getDailyDisplacementShareDTOByUserIdAndYear(Long userId, int year);
	List<ExpensesMonthDTO> getTimeMonthDTOByProjectId(Long projectId, Integer year);

}
