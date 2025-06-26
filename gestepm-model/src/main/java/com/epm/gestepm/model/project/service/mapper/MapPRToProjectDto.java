package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.project.dao.entity.Project;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapPRToProjectDto {

    ProjectDto from(Project entity);

    List<ProjectDto> from(List<Project> list);

    default Page<ProjectDto> from(Page<Project> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
