package com.epm.gestepm.modelapi.shares.construction.service;

import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareFileDto;
import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.construction.dto.deleter.ConstructionShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareFileByIdFinderDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ConstructionShareFileService {

    List<@Valid ConstructionShareFileDto> list(@Valid ConstructionShareFileFilterDto filterDto);
    
    Optional<@Valid ConstructionShareFileDto> find(@Valid ConstructionShareFileByIdFinderDto finderDto);

    @Valid
    ConstructionShareFileDto findOrNotFound(@Valid ConstructionShareFileByIdFinderDto finderDto);

    @Valid
    ConstructionShareFileDto create(@Valid ConstructionShareFileCreateDto createDto);

    void delete(@Valid ConstructionShareFileDeleteDto deleteDto);
    
}
