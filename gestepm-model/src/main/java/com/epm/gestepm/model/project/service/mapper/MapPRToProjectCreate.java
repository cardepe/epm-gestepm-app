package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.model.project.dao.entity.creator.ProjectCreate;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.creator.ProjectCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRToProjectCreate {

  ProjectCreate from(ProjectCreateDto createDto);

  ProjectCreateDto from(ProjectDto dto);

}
