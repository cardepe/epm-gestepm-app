package com.epm.gestepm.modelapi.deprecated.user.service;

import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.dto.UserDTO;

import java.util.List;

public interface UserServiceOld {
	
	User getUserById(Long id);
	List<User> findBySigningIds(List<Long> ids);
	User getUsuarioByEmailAndPassword(String email, String password);
	List<UserDTO> getAllUserDTOs();
	List<UserDTO> getUserDTOsByProjectId(Long projectId);
		List<ExpenseValidateDTO> getExpensesToValidateByUserId(Long userId);
	List<ExpenseUserValidateDTO> getExpensesToPay();

	void updateHolidaysInNewYear();
	void resetLastYearHolidays();
}
