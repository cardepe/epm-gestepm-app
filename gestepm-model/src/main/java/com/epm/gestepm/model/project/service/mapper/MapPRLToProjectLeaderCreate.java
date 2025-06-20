package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.model.project.dao.entity.creator.ProjectLeaderCreate;
import com.epm.gestepm.modelapi.project.dto.creator.ProjectLeaderCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRLToProjectLeaderCreate {

  ProjectLeaderCreate from(ProjectLeaderCreateDto createDto);

}
