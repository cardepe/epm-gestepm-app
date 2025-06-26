package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.model.project.dao.entity.deleter.ProjectMemberDelete;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectMemberDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMToProjectMemberDelete {

  ProjectMemberDelete from(ProjectMemberDeleteDto deleteDto);

}
