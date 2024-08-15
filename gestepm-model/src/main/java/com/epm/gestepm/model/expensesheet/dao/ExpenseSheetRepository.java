package com.epm.gestepm.model.expensesheet.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;

public interface ExpenseSheetRepository extends CrudRepository<ExpenseSheet, Long>, ExpenseSheetRepositoryCustom {

}
