package com.epm.gestepm.model.user.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectMemberDTO;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.dto.UserTableDTO;

import java.util.List;

public interface UserRepositoryCustom {
	List<UserDTO> findUserDTOsByProjectId(Long projectId);
	List<UserDTO> findAllUserDTOs();
	List<UserDTO> findNotUserDTOsByProjectId(Long projectId);
	List<UserDTO> findNotBossDTOsByProjectId(Long projectId);
	List<UserDTO> findAllProjectResponsables();
	List<ProjectMemberDTO> findAllMembersDTOByProjectId(Long projectId);
	List<ProjectMemberDTO> findProjectMemberDTOsByProjectId(Long projectId, PaginationCriteria pagination);
	List<ProjectMemberDTO> findProjectBossDTOsByProjectId(Long projectId, PaginationCriteria pagination);
	Long findProjectMembersCountByProjectId(Long projectId);
	Long findProjectBossesCountByProjectId(Long projectId);
	List<UserDTO> findUserDTOsByRank(Long rankId);
	List<UserTableDTO> findUsersDataTables(Integer state, List<Long> projectIds, PaginationCriteria pagination);
	Long findUsersCount(Integer state, List<Long> projectIds);
	UserTableDTO findUserDTOByUserId(Long userId, Integer state);
	List<ExpenseValidateDTO> findExpensesToValidateByUserId(Long userId);
	List<ExpenseUserValidateDTO> findExpensesToPay();
	String findFullNameById(Long userId);
}
