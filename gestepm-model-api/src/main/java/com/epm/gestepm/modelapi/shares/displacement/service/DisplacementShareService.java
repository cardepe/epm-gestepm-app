package com.epm.gestepm.modelapi.shares.displacement.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.displacement.dto.DisplacementShareDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.creator.DisplacementShareCreateDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.deleter.DisplacementShareDeleteDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.filter.DisplacementShareFilterDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.finder.DisplacementShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.updater.DisplacementShareUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface DisplacementShareService {

    List<@Valid DisplacementShareDto> list(@Valid DisplacementShareFilterDto filterDto);

    Page<@Valid DisplacementShareDto> list(@Valid DisplacementShareFilterDto filterDto, Long offset, Long limit);
    
    Optional<@Valid DisplacementShareDto> find(@Valid DisplacementShareByIdFinderDto finderDto);

    @Valid
    DisplacementShareDto findOrNotFound(@Valid DisplacementShareByIdFinderDto finderDto);

    @Valid
    DisplacementShareDto create(@Valid DisplacementShareCreateDto createDto);

    @Valid
    DisplacementShareDto update(@Valid DisplacementShareUpdateDto updateDto);

    void delete(@Valid DisplacementShareDeleteDto deleteDto);
}
