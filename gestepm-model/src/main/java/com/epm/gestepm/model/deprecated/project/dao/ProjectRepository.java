package com.epm.gestepm.model.deprecated.project.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.deprecated.project.dto.Project;

public interface ProjectRepository extends CrudRepository<Project, Long>, ProjectRepositoryCustom {

}
