package com.epm.gestepm.modelapi.project.service;

import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.finder.ProjectByIdFinderDto;

import javax.validation.Valid;

public interface ProjectDelegator {
    ProjectDto duplicate(@Valid ProjectByIdFinderDto finderDto);
}
