package com.epm.gestepm.modelapi.user.service;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectMemberDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.dto.UserTableDTO;

import java.util.List;

public interface UserService {
	
	User save(User user);
	void deleteUserById(Long id);
	void deleteUser(User user);
	User getUserById(Long id);
	List<User> findBySigningIds(List<Long> ids);
	User getUserBySigningId(Long signingId);
	User getUsuarioByEmailAndPassword(String email, String password);
	List<UserDTO> getAllUserDTOs();
	List<UserDTO> getUserDTOsByProjectId(Long projectId);
	List<UserDTO> getNotUserDTOsByProjectId(Long projectId);
	List<UserDTO> getAllProjectResponsables();
	List<ProjectMemberDTO> getProjectMemberDTOsByProjectId(Long projectId, PaginationCriteria pagination);
	Long getProjectMembersCountByProjectId(Long projectId);
	List<UserDTO> getNotBossDTOsByProjectId(Long projectId);
	List<ProjectMemberDTO> getAllMembersDTOByProjectId(Long projectId);
	List<ProjectMemberDTO> getProjectBossDTOsByProjectId(Long projectId, PaginationCriteria pagination);
	Long getProjectBossesCountByProjectId(Long projectId);
	List<UserDTO> getUserDTOsByRank(Long rankId);
	List<UserTableDTO> getUsersDataTables(Integer state, List<Long> projectIds, PaginationCriteria pagination);
	Long getUsersCount(Integer state, List<Long> projectIds);
	UserTableDTO getUserDTOByUserId(Long userId, Integer state);
	List<ExpenseValidateDTO> getExpensesToValidateByUserId(Long userId);
	List<ExpenseUserValidateDTO> getExpensesToPay();

	void updateHolidaysInNewYear();
	void resetLastYearHolidays();
}
