package com.epm.gestepm.model.deprecated.interventionprshare.dao;

import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.deprecated.interventionprshare.dto.InterventionPrShare;

import java.time.LocalDateTime;
import java.util.List;

public interface InterventionPrShareRepositoryCustom {

	List<InterventionPrShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
	List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);

}
