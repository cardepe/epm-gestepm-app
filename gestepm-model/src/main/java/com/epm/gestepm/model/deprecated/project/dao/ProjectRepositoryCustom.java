package com.epm.gestepm.model.deprecated.project.dao;

import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectListDTO;

import java.util.List;

public interface ProjectRepositoryCustom {
	List<ProjectListDTO> findAllProjectsDTOs();
}
