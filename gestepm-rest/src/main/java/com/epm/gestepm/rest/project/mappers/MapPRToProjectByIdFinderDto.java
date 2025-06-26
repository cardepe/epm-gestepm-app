package com.epm.gestepm.rest.project.mappers;

import com.epm.gestepm.modelapi.project.dto.finder.ProjectByIdFinderDto;
import com.epm.gestepm.rest.project.request.ProjectFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRToProjectByIdFinderDto {

  ProjectByIdFinderDto from(ProjectFindRestRequest req);

}
