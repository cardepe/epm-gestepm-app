package com.epm.gestepm.model.project.dao;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectTableDTO;

public interface ProjectRepositoryCustom {
	Project findByIdAndBossId(Long id, Long bossId);
	List<ProjectListDTO> findAllProjectsDTOs();
	List<ProjectListDTO> findBossProjectsDTOByUserId(Long userId);
	List<ProjectListDTO> findStationDTOs();
	List<ProjectListDTO> findByTeleworking(boolean teleworking);
	Long findAllProjectsCount(Object[] params);
	Long findProjectsCountByUserMember(Long userId, Object[] params);
	Long findProjectsCountByUserBoss(Long userId, Object[] params);
	List<ProjectTableDTO> findAllProjectsDataTables(PaginationCriteria pagination, Object[] params);
	List<ProjectTableDTO> findProjectsByUserMemberDataTables(Long userId, PaginationCriteria pagination, Object[] params);
	List<ProjectTableDTO> findProjectsByUserBossDataTables(Long userId, PaginationCriteria pagination, Object[] params);
	List<ProjectDTO> findNotProjectDTOsByUserId(Long userId);
	void createMember(Long projectId, Long userId);
	void deleteMember(Long projectId, Long userId);
	void createUserBoss(Long projectId, Long userId);
	void deleteUserBoss(Long projectId, Long userId);
}
