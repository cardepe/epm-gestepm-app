package com.epm.gestepm.modelapi.project.service;

import com.epm.gestepm.modelapi.project.dto.creator.ProjectLeaderCreateDto;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectLeaderDeleteDto;

import javax.validation.Valid;

public interface ProjectLeaderService {

    void create(@Valid ProjectLeaderCreateDto createDto);

    void delete(@Valid ProjectLeaderDeleteDto deleteDto);

}
