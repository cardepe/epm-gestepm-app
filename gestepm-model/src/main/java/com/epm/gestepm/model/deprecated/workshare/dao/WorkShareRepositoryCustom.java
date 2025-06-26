package com.epm.gestepm.model.deprecated.workshare.dao;

import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.deprecated.workshare.WorkShare;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkShareRepositoryCustom {

	List<WorkShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
	List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);

}
