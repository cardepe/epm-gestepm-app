package com.epm.gestepm.model.expensesheet.dao;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheetTableDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectExpenseSheetDTO;

public interface ExpenseSheetRepositoryCustom {
	public ExpenseSheet findByIdAndUserId(Long id, Long userId);
	public ExpenseSheet findByIdAndProjectId(Long id, Long projectId);
	public List<ExpenseSheet> findExpenseSheetsByProjectId(Long projectId);
	public List<ExpenseSheet> findExpenseSheetsByUserId(Long userId);
	public List<ExpenseSheet> findExpenseSheetsByUserIdAndStatus(Long userId, String status);
	public Long findExpenseSheetsCountByUserId(Long userId);
	public Long findExpenseSheetsCountByRRHH(Long userId);
	public List<ExpenseSheetTableDTO> findExpenseSheetsByUserDataTables(Long userId, PaginationCriteria pagination);
	public List<ExpenseSheetTableDTO> findExpenseSheetsByRRHHDataTables(Long userId, PaginationCriteria pagination);
	public List<ProjectExpenseSheetDTO> findProjectExpenseSheetDTOsByProjectId(Long projectId, PaginationCriteria pagination);
	public Long findProjectExpenseSheetDTOsCountByProjectId(Long projectId);
	public List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year);
	public Double findTotalYearExpensesByProjectId(Long projectId, Integer year);
}
