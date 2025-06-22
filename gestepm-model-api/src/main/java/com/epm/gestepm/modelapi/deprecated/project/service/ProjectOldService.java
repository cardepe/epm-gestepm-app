package com.epm.gestepm.modelapi.deprecated.project.service;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectTableDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Locale;

public interface ProjectOldService {
	
	Project getProjectById(Long id);
	List<Project> findDisplacementProjects();
	Project save(Project project);
	void delete(Long id);
	List<ProjectListDTO> getAllProjectsDTOs();
	List<ProjectListDTO> getProjectsByUser(User user);
	List<ProjectListDTO> getTeleworkingProjects(boolean isTeleworking);
	List<ProjectTableDTO> getAllProjectsDataTables(PaginationCriteria pagination, Object[] params);
	List<ProjectTableDTO> getProjectsByUserMemberDataTables(Long userId, PaginationCriteria pagination, Object[] params);
	List<ProjectDTO> getNotProjectDTOsByUserId(Long userId);
	Long getAllProjectsCount(Object[] params);
	Long getProjectsCountByUserMember(Long userId, Object[] params);
	void createMember(Long projectId, Long userId);
	void deleteMember(Long projectId, Long userId);
	void createUserBoss(Long projectId, Long userId);
	XSSFWorkbook generateProjectExcel(Long projectId, Long userId, Project project, Integer year, Locale locale);
}
