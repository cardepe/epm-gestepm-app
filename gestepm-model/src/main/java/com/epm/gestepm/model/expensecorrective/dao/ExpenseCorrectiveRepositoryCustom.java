package com.epm.gestepm.model.expensecorrective.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrectiveTableDTO;

import java.util.List;

public interface ExpenseCorrectiveRepositoryCustom {
	
	public Long findExpenseCorrectivesCountByUserId(Long userId);
	public List<ExpenseCorrectiveTableDTO> findExpenseCorrectivesByUserDataTables(Long userId, PaginationCriteria pagination);
	public ExpensesMonthDTO findExpensesMonthDTOByProjectId(Long projectId, Integer year);
	public Double findTotalYearExpensesByProjectId(Long projectId, Integer year);
}
