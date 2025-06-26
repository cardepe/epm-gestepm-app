package com.epm.gestepm.rest.project.mappers;

import com.epm.gestepm.modelapi.project.dto.updater.ProjectUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateProjectV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRToProjectUpdateDto {

  ProjectUpdateDto from(UpdateProjectV1Request req);

}
