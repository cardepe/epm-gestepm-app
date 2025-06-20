package com.epm.gestepm.rest.project.mappers;

import com.epm.gestepm.modelapi.project.dto.creator.ProjectLeaderCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateProjectLeaderV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRLToProjectLeaderCreateDto {

  ProjectLeaderCreateDto from(CreateProjectLeaderV1Request req);

}
