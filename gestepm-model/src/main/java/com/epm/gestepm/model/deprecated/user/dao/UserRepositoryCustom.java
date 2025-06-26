package com.epm.gestepm.model.deprecated.user.dao;

import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.UserDTO;

import java.util.List;

public interface UserRepositoryCustom {
	List<UserDTO> findUserDTOsByProjectId(Long projectId);
	List<UserDTO> findAllUserDTOs();
	List<ExpenseValidateDTO> findExpensesToValidateByUserId(Long userId);
	List<ExpenseUserValidateDTO> findExpensesToPay();
	String findFullNameById(Long userId);
}
