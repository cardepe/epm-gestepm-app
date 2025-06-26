package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.model.project.dao.entity.creator.ProjectMemberCreate;
import com.epm.gestepm.modelapi.project.dto.creator.ProjectMemberCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMToProjectMemberCreate {

  ProjectMemberCreate from(ProjectMemberCreateDto createDto);

}
