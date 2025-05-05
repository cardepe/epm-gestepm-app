package com.epm.gestepm.modelapi.shares.work.service;

import com.epm.gestepm.modelapi.shares.work.dto.WorkShareFileDto;
import com.epm.gestepm.modelapi.shares.work.dto.creator.WorkShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.work.dto.deleter.WorkShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.work.dto.filter.WorkShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.work.dto.finder.WorkShareFileByIdFinderDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface WorkShareFileService {

    List<@Valid WorkShareFileDto> list(@Valid WorkShareFileFilterDto filterDto);
    
    Optional<@Valid WorkShareFileDto> find(@Valid WorkShareFileByIdFinderDto finderDto);

    @Valid
    WorkShareFileDto findOrNotFound(@Valid WorkShareFileByIdFinderDto finderDto);

    @Valid
    WorkShareFileDto create(@Valid WorkShareFileCreateDto createDto);

    void delete(@Valid WorkShareFileDeleteDto deleteDto);
    
}
