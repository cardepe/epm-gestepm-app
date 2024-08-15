package com.epm.gestepm.modelapi.expensefile.service;


import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;

public interface ExpenseFileService {

	ExpenseFile save(ExpenseFile expenseFile);
	
	ExpenseFile getExpenseFileById(Long expenseFileId);
	
	ExpenseFile getExpenseFileByExpenseId(Long expenseId);

}
