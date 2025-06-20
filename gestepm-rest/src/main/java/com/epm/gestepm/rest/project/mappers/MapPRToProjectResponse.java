package com.epm.gestepm.rest.project.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.restapi.openapi.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapPRToProjectResponse {

  @Mapping(source = "activityCenterId", target = "activityCenter.id")
  Project from(ProjectDto dto);

  List<Project> from(Page<ProjectDto> list);

}
