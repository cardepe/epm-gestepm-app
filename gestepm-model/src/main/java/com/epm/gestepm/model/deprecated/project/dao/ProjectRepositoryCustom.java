package com.epm.gestepm.model.deprecated.project.dao;

import java.util.List;

import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectListDTO;

public interface ProjectRepositoryCustom {
	List<ProjectListDTO> findAllProjectsDTOs();
}
