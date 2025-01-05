package com.epm.gestepm.modelapi.expensesheet.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheetTableDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectExpenseSheetDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

public interface ExpenseSheetService {
	
	ExpenseSheet save(ExpenseSheet expenseSheet);
	
	ExpenseSheet create(ExpenseSheet expenseSheet);
	
	ExpenseSheet getExpenseSheetByIdAndUserId(Long expenseSheetId, Long userId);
	
	ExpenseSheet getExpenseSheetByIdAndProjectId(Long expenseSheetId, Long project);
	
	ExpenseSheet getExpenseSheetById(Long expenseSheetId);
	
	Long getExpenseSheetsCountByUser(Long userId);
	
	Long getExpenseSheetsCountByRRHH(Long userId);
		
	void deleteById(Long expenseSheetId);
	
	List<ExpenseSheetTableDTO> getExpenseSheetsByUserDataTables(Long userId, PaginationCriteria pagination);
	
	List<ExpenseSheetTableDTO> getExpenseSheetsByRRHHDataTables(Long userId, PaginationCriteria pagination);
		
	List<ProjectExpenseSheetDTO> getProjectExpenseSheetDTOsByProjectId(Long projectId, PaginationCriteria pagination);
	
	Long getProjectExpenseSheetDTOsCountByProjectId(Long projectId);

}
