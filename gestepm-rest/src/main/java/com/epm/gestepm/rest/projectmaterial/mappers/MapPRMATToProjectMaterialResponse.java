package com.epm.gestepm.rest.projectmaterial.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.projectmaterial.dto.ProjectMaterialDto;
import com.epm.gestepm.restapi.openapi.model.ProjectMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapPRMATToProjectMaterialResponse {

  @Mapping(source = "projectId", target = "project.id")
  ProjectMaterial from(ProjectMaterialDto dto);

  List<ProjectMaterial> from(Page<ProjectMaterialDto> list);

}
