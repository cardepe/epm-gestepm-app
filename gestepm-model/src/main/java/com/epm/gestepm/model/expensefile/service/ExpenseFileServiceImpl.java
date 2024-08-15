package com.epm.gestepm.model.expensefile.service;

import com.epm.gestepm.model.expensefile.dao.ExpenseFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;
import com.epm.gestepm.modelapi.expensefile.service.ExpenseFileService;

@Service
@Transactional
public class ExpenseFileServiceImpl implements ExpenseFileService {

	@Autowired
	private ExpenseFileRepository expenseFileRepository;

	@Override
	public ExpenseFile save(ExpenseFile expenseFile) {
		return expenseFileRepository.save(expenseFile);
	}
	
	@Override
	public ExpenseFile getExpenseFileById(Long expenseFileId) {
		return expenseFileRepository.findById(expenseFileId).orElse(null);
	}
	
	@Override
	public ExpenseFile getExpenseFileByExpenseId(Long expenseId) {
		return expenseFileRepository.findByExpenseId(expenseId);
	}
}
