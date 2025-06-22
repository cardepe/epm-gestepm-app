package com.epm.gestepm.model.projectmaterial.service.mapper;

import com.epm.gestepm.model.projectmaterial.dao.entity.creator.ProjectMaterialCreate;
import com.epm.gestepm.modelapi.projectmaterial.dto.creator.ProjectMaterialCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMATToProjectMaterialCreate {

  ProjectMaterialCreate from(ProjectMaterialCreateDto createDto);

}
