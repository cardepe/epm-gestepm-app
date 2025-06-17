package com.epm.gestepm.model.userold.service;

import com.epm.gestepm.model.userold.dao.UserRepository;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectMemberDTO;
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

	public List<UserDTO> getNotBossDTOsByProjectId(Long projectId) {
		return userRepository.findNotBossDTOsByProjectId(projectId);
	}

	public List<UserDTO> getAllProjectResponsables() {
		return userRepository.findAllProjectResponsables();
	}

	public List<UserDTO> getUserDTOsByProjectId(Long projectId) {
		return userRepository.findUserDTOsByProjectId(projectId);
	}

	public List<UserDTO> getNotUserDTOsByProjectId(Long projectId) {
		return userRepository.findNotUserDTOsByProjectId(projectId);
	}

	public List<ProjectMemberDTO> getProjectMemberDTOsByProjectId(Long projectId, PaginationCriteria pagination) {
		return userRepository.findProjectMemberDTOsByProjectId(projectId, pagination);
	}

	public Long getProjectMembersCountByProjectId(Long projectId) {
		return userRepository.findProjectMembersCountByProjectId(projectId);
	}

	public List<ProjectMemberDTO> getProjectBossDTOsByProjectId(Long projectId, PaginationCriteria pagination) {
		return userRepository.findProjectBossDTOsByProjectId(projectId, pagination);
	}

	public Long getProjectBossesCountByProjectId(Long projectId) {
		return userRepository.findProjectBossesCountByProjectId(projectId);
	}

	public List<UserDTO> getUserDTOsByRank(Long rankId) {
		return userRepository.findUserDTOsByRank(rankId);
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
