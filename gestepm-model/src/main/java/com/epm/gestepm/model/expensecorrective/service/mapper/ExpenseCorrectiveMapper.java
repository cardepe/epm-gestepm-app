package com.epm.gestepm.model.expensecorrective.service.mapper;

import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrectiveDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;

public class ExpenseCorrectiveMapper {

	private ExpenseCorrectiveMapper() {
		
	}

	public static ExpenseCorrective mapDTOToExpenseCorrective(ExpenseCorrectiveDTO expenseCorrectiveDTO, User user, Project project) {
		
		ExpenseCorrective expenseCorrective = new ExpenseCorrective();
		expenseCorrective.setUser(user);
		expenseCorrective.setProject(project);
		expenseCorrective.setCost(expenseCorrectiveDTO.getCost());
		expenseCorrective.setDescription(expenseCorrectiveDTO.getDescription());
		
		return expenseCorrective;
	}
}
