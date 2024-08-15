package com.epm.gestepm.modelapi.project.service;

import java.util.List;
import java.util.Locale;

import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectTableDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

public interface ProjectService {
	
	Project getProjectById(Long id);
	List<Project> getAllProjects();
	List<Project> findDisplacementProjects();
	Project getProjectByIdAndUserId(Long id, Long userId);
	Project getProjectByIdAndBossId(Long id, Long bossId);
	Project save(Project project);
	void delete(Long id);
	List<ProjectListDTO> getAllProjectsDTOs();
	List<ProjectListDTO> getProjectsDTOByUserId(Long userId);
	List<ProjectListDTO> getBossProjectsDTOByUserId(Long userId);
	List<ProjectListDTO> getStationDTOs();
	List<ProjectTableDTO> getAllProjectsDataTables(PaginationCriteria pagination, Object[] params);
	List<ProjectTableDTO> getProjectsByUserMemberDataTables(Long userId, PaginationCriteria pagination, Object[] params);
	List<ProjectTableDTO> getProjectsByUserBossDataTables(Long userId, PaginationCriteria pagination, Object[] params);
	List<ProjectDTO> getNotProjectDTOsByUserId(Long userId);
	Long getAllProjectsCount(Object[] params);
	Long getProjectsCountByUserMember(Long userId, Object[] params);
	Long getProjectsCountByUserBoss(Long userId, Object[] params);
	void createMember(Long projectId, Long userId);
	void deleteMember(Long projectId, Long userId);
	void createUserBoss(Long projectId, Long userId);
	void deleteUserBoss(Long projectId, Long userId);
	void deleteAllUserBossByUserId(Long userId);
	XSSFWorkbook generateProjectExcel(Long projectId, Long userId, Project project, Integer year, Locale locale);
}
