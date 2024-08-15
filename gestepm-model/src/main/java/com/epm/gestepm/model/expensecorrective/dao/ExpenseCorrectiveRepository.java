package com.epm.gestepm.model.expensecorrective.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;

public interface ExpenseCorrectiveRepository extends CrudRepository<ExpenseCorrective, Long>, ExpenseCorrectiveRepositoryCustom {

}
