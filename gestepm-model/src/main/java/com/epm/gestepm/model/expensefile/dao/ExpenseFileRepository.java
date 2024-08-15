package com.epm.gestepm.model.expensefile.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;

public interface ExpenseFileRepository extends CrudRepository<ExpenseFile, Long>, ExpenseFileRepositoryCustom {

}
