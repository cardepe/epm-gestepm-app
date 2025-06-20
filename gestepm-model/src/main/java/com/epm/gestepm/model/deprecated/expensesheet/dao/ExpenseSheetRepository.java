package com.epm.gestepm.model.deprecated.expensesheet.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.deprecated.expensesheet.dto.ExpenseSheet;

public interface ExpenseSheetRepository extends CrudRepository<ExpenseSheet, Long>, ExpenseSheetRepositoryCustom {

}
