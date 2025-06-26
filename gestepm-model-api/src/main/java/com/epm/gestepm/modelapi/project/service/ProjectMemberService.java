package com.epm.gestepm.modelapi.project.service;

import com.epm.gestepm.modelapi.project.dto.creator.ProjectMemberCreateDto;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectMemberDeleteDto;

import javax.validation.Valid;

public interface ProjectMemberService {

    void create(@Valid ProjectMemberCreateDto createDto);

    void delete(@Valid ProjectMemberDeleteDto deleteDto);

}
