package com.epm.gestepm.modelapi.shares.construction.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareCreateDto;
import com.epm.gestepm.modelapi.shares.construction.dto.deleter.ConstructionShareDeleteDto;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFilterDto;
import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ConstructionShareService {

    List<@Valid ConstructionShareDto> list(@Valid ConstructionShareFilterDto filterDto);

    Page<@Valid ConstructionShareDto> list(@Valid ConstructionShareFilterDto filterDto, Long offset, Long limit);
    
    Optional<@Valid ConstructionShareDto> find(@Valid ConstructionShareByIdFinderDto finderDto);

    @Valid
    ConstructionShareDto findOrNotFound(@Valid ConstructionShareByIdFinderDto finderDto);

    @Valid
    ConstructionShareDto create(@Valid ConstructionShareCreateDto createDto);

    @Valid
    ConstructionShareDto update(@Valid ConstructionShareUpdateDto updateDto);

    void delete(@Valid ConstructionShareDeleteDto deleteDto);
}
