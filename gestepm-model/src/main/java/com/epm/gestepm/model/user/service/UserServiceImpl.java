package com.epm.gestepm.model.user.service;

import com.epm.gestepm.model.user.dao.UserRepository;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectMemberDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.dto.UserTableDTO;
import com.epm.gestepm.modelapi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

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

	public User getUserBySigningId(Long signingId) {
		return userRepository.findBySigningId(signingId);
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

	public List<ProjectMemberDTO> getAllMembersDTOByProjectId(Long projectId) {
		return userRepository.findAllMembersDTOByProjectId(projectId);
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

	public List<UserTableDTO> getUsersDataTables(Integer state, List<Long> projectIds, PaginationCriteria pagination) {
		return userRepository.findUsersDataTables(state, projectIds, pagination);
	}

	public Long getUsersCount(Integer state, List<Long> projectIds) {
		return userRepository.findUsersCount(state, projectIds);
	}

	public UserTableDTO getUserDTOByUserId(Long userId, Integer state) {
		return userRepository.findUserDTOByUserId(userId, state);
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
