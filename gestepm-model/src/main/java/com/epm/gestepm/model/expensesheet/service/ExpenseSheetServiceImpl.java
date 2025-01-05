package com.epm.gestepm.model.expensesheet.service;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.epm.gestepm.model.expensefile.dao.ExpenseFileRepository;
import com.epm.gestepm.model.expense.dao.ExpenseRepository;
import com.epm.gestepm.model.expensesheet.dao.ExpenseSheetRepository;
import com.epm.gestepm.modelapi.common.utils.ExcelUtils;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheetTableDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectExpenseSheetDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.pricetype.dto.PriceType;
import com.epm.gestepm.modelapi.expensesheet.service.ExpenseSheetService;
import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.model.common.utils.classes.SingletonUtil;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

@Service
@Transactional
public class ExpenseSheetServiceImpl implements ExpenseSheetService {
	
	@Autowired
	private ExpenseSheetRepository expenseSheetRepository;
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private ExpenseFileRepository expenseFileRepository;

	@Override
	public ExpenseSheet save(ExpenseSheet expenseSheet) {
		return expenseSheetRepository.save(expenseSheet);
	}
	
	@Override
	public ExpenseSheet create(ExpenseSheet expenseSheet) {
		
		expenseSheet = expenseSheetRepository.save(expenseSheet);
		
		List<Expense> expenses = expenseSheet.getExpenses();
		
		if (expenses != null && !expenses.isEmpty()) {
			
			for (Expense expense : expenses) {

				expense.setExpenseSheet(expenseSheet);
				expense = expenseRepository.save(expense);
				
				List<ExpenseFile> expensesFile = expense.getFiles();
				
				if (expensesFile != null && !expensesFile.isEmpty()) {
					
					for (ExpenseFile expenseFile : expensesFile) {
						
						expenseFile.setExpense(expense);
						expenseFileRepository.save(expenseFile);
					}
				}
			}		
		}
		
		return expenseSheet;
	}
	
	@Override
	public ExpenseSheet getExpenseSheetById(Long expenseSheetId) {
		return expenseSheetRepository.findById(expenseSheetId).orElse(null);
	}
	
	@Override
	public ExpenseSheet getExpenseSheetByIdAndUserId(Long expenseSheetId, Long userId) {
		return expenseSheetRepository.findByIdAndUserId(expenseSheetId, userId);
	}
	
	@Override
	public ExpenseSheet getExpenseSheetByIdAndProjectId(Long expenseSheetId, Long projectId) {
		return expenseSheetRepository.findByIdAndProjectId(expenseSheetId, projectId);
	}
	
	@Override
	public Long getExpenseSheetsCountByUser(Long userId) {
		return expenseSheetRepository.findExpenseSheetsCountByUserId(userId);
	}
	
	@Override
	public Long getExpenseSheetsCountByRRHH(Long userId) {
		return expenseSheetRepository.findExpenseSheetsCountByRRHH(userId);
	}

	@Override
	public void deleteById(Long expenseSheetId) {
		expenseSheetRepository.deleteById(expenseSheetId);
	}
	
	@Override
	public List<ExpenseSheetTableDTO> getExpenseSheetsByUserDataTables(Long userId, PaginationCriteria pagination) {
		return expenseSheetRepository.findExpenseSheetsByUserDataTables(userId, pagination);
	}
	
	@Override
	public List<ExpenseSheetTableDTO> getExpenseSheetsByRRHHDataTables(Long userId, PaginationCriteria pagination) {
		return expenseSheetRepository.findExpenseSheetsByRRHHDataTables(userId, pagination);
	}

	@Override
	public List<ProjectExpenseSheetDTO> getProjectExpenseSheetDTOsByProjectId(Long projectId, PaginationCriteria pagination) {
		return expenseSheetRepository.findProjectExpenseSheetDTOsByProjectId(projectId, pagination);
	}
	
	@Override
	public Long getProjectExpenseSheetDTOsCountByProjectId(Long projectId) {
		return expenseSheetRepository.findProjectExpenseSheetDTOsCountByProjectId(projectId);
	}
}
