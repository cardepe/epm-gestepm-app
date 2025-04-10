package com.epm.gestepm.model.displacementshare.dao;

import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface DisplacementShareRepositoryCustom {

	List<DisplacementShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId, Integer manual);
	List<ExpensesMonthDTO> findTimeMonthDTOByProjectId(Long projectId, Integer year);

}
