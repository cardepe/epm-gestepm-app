package com.epm.gestepm.modelapi.deprecated.project.service;

import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Locale;

public interface ProjectOldService {
	
	Project getProjectById(Long id);
	List<ProjectListDTO> getAllProjectsDTOs();
	List<ProjectListDTO> getProjectsByUser(User user);
	XSSFWorkbook generateProjectExcel(Long projectId, Long userId, Project project, Integer year, Locale locale);
}
