package com.epm.gestepm.model.userold.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectMemberDTO;
import com.epm.gestepm.modelapi.userold.dto.UserDTO;

import java.util.List;

public interface UserRepositoryCustom {
	List<UserDTO> findUserDTOsByProjectId(Long projectId);
	List<UserDTO> findAllUserDTOs();
	List<UserDTO> findNotUserDTOsByProjectId(Long projectId);
	List<UserDTO> findNotBossDTOsByProjectId(Long projectId);
	List<UserDTO> findAllProjectResponsables();
	List<ProjectMemberDTO> findProjectMemberDTOsByProjectId(Long projectId, PaginationCriteria pagination);
	List<ProjectMemberDTO> findProjectBossDTOsByProjectId(Long projectId, PaginationCriteria pagination);
	Long findProjectMembersCountByProjectId(Long projectId);
	Long findProjectBossesCountByProjectId(Long projectId);
	List<UserDTO> findUserDTOsByRank(Long rankId);
	List<ExpenseValidateDTO> findExpensesToValidateByUserId(Long userId);
	List<ExpenseUserValidateDTO> findExpensesToPay();
	String findFullNameById(Long userId);
}
