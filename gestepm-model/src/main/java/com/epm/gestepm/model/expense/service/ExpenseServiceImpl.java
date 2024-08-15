package com.epm.gestepm.model.expense.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.epm.gestepm.model.expense.dao.ExpenseRepository;
import com.epm.gestepm.modelapi.expense.dto.ExpenseTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.expense.service.ExpenseService;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	public Expense save(Expense expense) {
		return expenseRepository.save(expense);
	}
	
	@Override
	public Expense getExpenseById(Long expenseId) {
		return expenseRepository.findById(expenseId).orElse(null);
	}

	@Override
	public List<Expense> getExpensesBySheet(Long sheetId) {
		return expenseRepository.findExpensesBySheetId(sheetId);
	}
	
	@Override
	public Long getExpensesCountBySheet(Long sheetId) {
		return expenseRepository.findExpensesCountBySheetId(sheetId);
	}

	@Override
	public void deleteById(Long expenseId) {
		expenseRepository.deleteById(expenseId);
	}
	
	@Override
	public List<ExpenseTableDTO> getExpensesBySheetDataTables(Long sheetId, PaginationCriteria pagination) {
		return expenseRepository.findExpensesBySheetDataTables(sheetId, pagination);
	}

	public double getTotalPendingAmount(List<Expense> expenses) {
		double amount = 0;

		for (Expense expense : expenses) {
			amount += expense.getTotal();
		}

		return amount;

	}
	
	public Date getLastExpenseDate (List<Expense> expenses) {
		
		if (expenses.isEmpty()) {
			return null;
		}
			
		Collections.sort(expenses, new Comparator<Expense>() {
			  public int compare(Expense o1, Expense o2) {
			      return o1.getDate().compareTo(o2.getDate());
			  }
			});
		
		return expenses.get(expenses.size() - 1).getDate();
	}

}
