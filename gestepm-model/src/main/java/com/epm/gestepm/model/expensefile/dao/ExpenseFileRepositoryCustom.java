package com.epm.gestepm.model.expensefile.dao;

import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;

public interface ExpenseFileRepositoryCustom {
	ExpenseFile findByExpenseId(Long expenseId);
}
