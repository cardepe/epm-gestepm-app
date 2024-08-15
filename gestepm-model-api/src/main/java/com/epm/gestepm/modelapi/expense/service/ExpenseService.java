package com.epm.gestepm.modelapi.expense.service;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.expense.dto.ExpenseTableDTO;

public interface ExpenseService {
	
	Expense save(Expense expense);
	
	Expense getExpenseById(Long expenseId);
		
	List<Expense> getExpensesBySheet(Long sheetId);
		
	Long getExpensesCountBySheet(Long sheetId);
	
	void deleteById(Long expenseId);
	
	List<ExpenseTableDTO> getExpensesBySheetDataTables(Long sheetId, PaginationCriteria pagination);
			
	public double getTotalPendingAmount(List<Expense> expenses);
	
	public Date getLastExpenseDate (List<Expense> expenses);
	
		
}
