package com.epm.gestepm.model.expensecorrective.service;

import java.util.List;

import com.epm.gestepm.model.expensecorrective.dao.ExpenseCorrectiveRepository;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrectiveTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.expensecorrective.service.ExpenseCorrectiveService;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

@Service
@Transactional
public class ExpenseCorrectiveServiceImpl implements ExpenseCorrectiveService {
	
	@Autowired
	private ExpenseCorrectiveRepository expenseCorrectiveRepository;

	public ExpenseCorrective getById(long id) {
		return expenseCorrectiveRepository.findById(id).orElse(null);
	}
	
	@Override
	public ExpenseCorrective save(ExpenseCorrective expenseCorrective) {
		return expenseCorrectiveRepository.save(expenseCorrective);
	}
	
	@Override
	public void delete(Long id) {
		expenseCorrectiveRepository.deleteById(id);
	}
	
	@Override
	public Long getExpenseCorrectivesCountByUser(Long userId) {
		return expenseCorrectiveRepository.findExpenseCorrectivesCountByUserId(userId);
	}
	
	@Override
	public List<ExpenseCorrectiveTableDTO> getExpenseCorrectivesByUserDataTables(Long userId, PaginationCriteria pagination) {
		return expenseCorrectiveRepository.findExpenseCorrectivesByUserDataTables(userId, pagination);
	}
}
