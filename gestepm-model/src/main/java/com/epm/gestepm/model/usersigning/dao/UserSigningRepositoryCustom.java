package com.epm.gestepm.model.usersigning.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigningTableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface UserSigningRepositoryCustom {

	List<UserSigningTableDTO> findUserSigningDTOsByUserId(Long userId, PaginationCriteria pagination);
	Long findUserSigningCountByUserId(Long userId);
	List<UserSigning> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
	List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);

}
