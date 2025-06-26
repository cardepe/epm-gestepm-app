package com.epm.gestepm.model.deprecated.user.service;

import com.epm.gestepm.model.deprecated.user.dao.UserRepository;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.dto.UserDTO;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceOldImpl implements UserServiceOld {

	@Autowired
	private UserRepository userRepository;

	public User getUsuarioByEmailAndPassword(String email, String password) {
		return userRepository.findUsuarioByEmailAndPassword(email, password);
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public List<User> findBySigningIds(List<Long> ids) {
		return userRepository.findBySigningIds(ids);
	}

	public List<UserDTO> getAllUserDTOs() {
		return userRepository.findAllUserDTOs();
	}

	public List<UserDTO> getUserDTOsByProjectId(Long projectId) {
		return userRepository.findUserDTOsByProjectId(projectId);
	}

	public List<ExpenseValidateDTO> getExpensesToValidateByUserId(Long userId) {
		return userRepository.findExpensesToValidateByUserId(userId);
	}

	public List<ExpenseUserValidateDTO> getExpensesToPay() {
		return userRepository.findExpensesToPay().stream().filter(e -> e.getExpenseSheetsCount() > 0).sorted(Comparator.comparing(ExpenseUserValidateDTO::getName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
	}

	@Override
	public void updateHolidaysInNewYear() {
		this.userRepository.updateHolidaysInNewYear();
	}

	@Override
	public void resetLastYearHolidays() {
		this.userRepository.resetLastYearHolidays();
	}
}
