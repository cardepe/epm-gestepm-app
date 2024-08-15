package com.epm.gestepm.model.expense.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.expense.dto.ExpenseTableDTO;

import java.util.List;

public interface ExpenseRepositoryCustom {
	public List<Expense> findExpensesBySheetId(Long sheetId);
	public Long findExpensesCountBySheetId(Long sheetId);
	public List<ExpenseTableDTO> findExpensesBySheetDataTables(Long sheetId, PaginationCriteria pagination);
}
