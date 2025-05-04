package com.epm.gestepm.model.expensesheet.dao;

import java.util.List;

import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpensesMonthDTO;

public interface ExpenseSheetRepositoryCustom {
	List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);
	Double findTotalYearExpensesByProjectId(Long projectId, Integer year);
}
