package com.epm.gestepm.model.expense.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.expense.dto.Expense;

public interface ExpenseRepository extends CrudRepository<Expense, Long>, ExpenseRepositoryCustom {

}
