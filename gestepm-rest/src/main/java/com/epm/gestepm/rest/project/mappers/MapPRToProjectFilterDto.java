package com.epm.gestepm.rest.project.mappers;

import com.epm.gestepm.modelapi.project.dto.filter.ProjectFilterDto;
import com.epm.gestepm.rest.project.request.ProjectListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRToProjectFilterDto {

  ProjectFilterDto from(ProjectListRestRequest req);

}
