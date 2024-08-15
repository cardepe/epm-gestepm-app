package com.epm.gestepm.model.usersigning.dao;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigningTableDTO;

public interface UserSigningRepositoryCustom {

	List<UserSigningTableDTO> findUserSigningDTOsByUserId(Long userId, PaginationCriteria pagination);
	Long findUserSigningCountByUserId(Long userId);
	List<UserSigning> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);

}
