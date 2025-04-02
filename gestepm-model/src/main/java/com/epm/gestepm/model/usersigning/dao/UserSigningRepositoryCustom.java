package com.epm.gestepm.model.usersigning.dao;

import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;

import java.time.LocalDateTime;
import java.util.List;

public interface UserSigningRepositoryCustom {

	List<UserSigning> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
	List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);

}
