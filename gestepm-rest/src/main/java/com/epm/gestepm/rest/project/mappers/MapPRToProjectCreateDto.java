package com.epm.gestepm.rest.project.mappers;

import com.epm.gestepm.modelapi.project.dto.creator.ProjectCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateProjectV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRToProjectCreateDto {

  ProjectCreateDto from(CreateProjectV1Request req);

}
