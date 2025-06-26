package com.epm.gestepm.model.deprecated.displacementshare.dao;

import com.epm.gestepm.modelapi.deprecated.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpensesMonthDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface DisplacementShareRepositoryCustom {

	List<DisplacementShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userI);
	List<ExpensesMonthDTO> findTimeMonthDTOByProjectId(Long projectId, Integer year);

}
