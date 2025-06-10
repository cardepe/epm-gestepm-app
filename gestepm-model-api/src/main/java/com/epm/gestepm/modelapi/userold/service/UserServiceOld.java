package com.epm.gestepm.modelapi.userold.service;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectMemberDTO;
import com.epm.gestepm.modelapi.userold.dto.User;
import com.epm.gestepm.modelapi.userold.dto.UserDTO;

import java.util.List;

public interface UserServiceOld {
	
	User getUserById(Long id);
	List<User> findBySigningIds(List<Long> ids);
	User getUsuarioByEmailAndPassword(String email, String password);
	List<UserDTO> getAllUserDTOs();
	List<UserDTO> getUserDTOsByProjectId(Long projectId);
	List<UserDTO> getNotUserDTOsByProjectId(Long projectId);
	List<UserDTO> getAllProjectResponsables();
	List<ProjectMemberDTO> getProjectMemberDTOsByProjectId(Long projectId, PaginationCriteria pagination);
	Long getProjectMembersCountByProjectId(Long projectId);
	List<UserDTO> getNotBossDTOsByProjectId(Long projectId);
	List<ProjectMemberDTO> getProjectBossDTOsByProjectId(Long projectId, PaginationCriteria pagination);
	Long getProjectBossesCountByProjectId(Long projectId);
	List<UserDTO> getUserDTOsByRank(Long rankId);
		List<ExpenseValidateDTO> getExpensesToValidateByUserId(Long userId);
	List<ExpenseUserValidateDTO> getExpensesToPay();

	void updateHolidaysInNewYear();
	void resetLastYearHolidays();
}
