package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.model.project.dao.entity.filter.ProjectFilter;
import com.epm.gestepm.modelapi.project.dto.filter.ProjectFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRToProjectFilter {

    ProjectFilter from(ProjectFilterDto filterDto);

}
