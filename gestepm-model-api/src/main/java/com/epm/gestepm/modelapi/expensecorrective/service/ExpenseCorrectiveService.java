package com.epm.gestepm.modelapi.expensecorrective.service;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrectiveTableDTO;

public interface ExpenseCorrectiveService {
	
	ExpenseCorrective getById(long id);
	ExpenseCorrective save(ExpenseCorrective expenseCorrective);
	void delete(Long id);
	Long getExpenseCorrectivesCountByUser(Long userId);
	List<ExpenseCorrectiveTableDTO> getExpenseCorrectivesByUserDataTables(Long userId, PaginationCriteria pagination);
}
