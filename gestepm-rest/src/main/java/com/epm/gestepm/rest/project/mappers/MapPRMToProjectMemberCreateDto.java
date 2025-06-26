package com.epm.gestepm.rest.project.mappers;

import com.epm.gestepm.modelapi.project.dto.creator.ProjectMemberCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateProjectMemberV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMToProjectMemberCreateDto {

  ProjectMemberCreateDto from(CreateProjectMemberV1Request req);

}
