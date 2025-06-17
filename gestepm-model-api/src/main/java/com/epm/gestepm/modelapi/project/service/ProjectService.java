package com.epm.gestepm.modelapi.project.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.creator.ProjectCreateDto;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectDeleteDto;
import com.epm.gestepm.modelapi.project.dto.filter.ProjectFilterDto;
import com.epm.gestepm.modelapi.project.dto.finder.ProjectByIdFinderDto;
import com.epm.gestepm.modelapi.project.dto.updater.ProjectUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<@Valid ProjectDto> list(@Valid ProjectFilterDto filterDto);

    Page<@Valid ProjectDto> list(@Valid ProjectFilterDto filterDto, Long offset, Long limit);
    
    Optional<@Valid ProjectDto> find(@Valid ProjectByIdFinderDto finderDto);

    @Valid
    ProjectDto findOrNotFound(@Valid ProjectByIdFinderDto finderDto);

    @Valid
    ProjectDto create(@Valid ProjectCreateDto createDto);

    @Valid
    ProjectDto update(@Valid ProjectUpdateDto updateDto);

    void delete(@Valid ProjectDeleteDto deleteDto);

}
