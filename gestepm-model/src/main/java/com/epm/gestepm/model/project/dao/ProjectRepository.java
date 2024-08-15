package com.epm.gestepm.model.project.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.project.dto.Project;

public interface ProjectRepository extends CrudRepository<Project, Long>, ProjectRepositoryCustom {

}
