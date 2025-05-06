package com.epm.gestepm.modelapi.shares.work.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareDto;
import com.epm.gestepm.modelapi.shares.work.dto.creator.WorkShareCreateDto;
import com.epm.gestepm.modelapi.shares.work.dto.deleter.WorkShareDeleteDto;
import com.epm.gestepm.modelapi.shares.work.dto.filter.WorkShareFilterDto;
import com.epm.gestepm.modelapi.shares.work.dto.finder.WorkShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.work.dto.updater.WorkShareUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface WorkShareService {

    List<@Valid WorkShareDto> list(@Valid WorkShareFilterDto filterDto);

    Page<@Valid WorkShareDto> list(@Valid WorkShareFilterDto filterDto, Long offset, Long limit);
    
    Optional<@Valid WorkShareDto> find(@Valid WorkShareByIdFinderDto finderDto);

    @Valid
    WorkShareDto findOrNotFound(@Valid WorkShareByIdFinderDto finderDto);

    @Valid
    WorkShareDto create(@Valid WorkShareCreateDto createDto);

    @Valid
    WorkShareDto update(@Valid WorkShareUpdateDto updateDto);

    void delete(@Valid WorkShareDeleteDto deleteDto);
}
