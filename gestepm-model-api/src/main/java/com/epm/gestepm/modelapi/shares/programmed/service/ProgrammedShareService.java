package com.epm.gestepm.modelapi.shares.programmed.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.creator.ProgrammedShareCreateDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.deleter.ProgrammedShareDeleteDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFilterDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.finder.ProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.updater.ProgrammedShareUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProgrammedShareService {

    List<@Valid ProgrammedShareDto> list(@Valid ProgrammedShareFilterDto filterDto);

    Page<@Valid ProgrammedShareDto> list(@Valid ProgrammedShareFilterDto filterDto, Long offset, Long limit);
    
    Optional<@Valid ProgrammedShareDto> find(@Valid ProgrammedShareByIdFinderDto finderDto);

    @Valid
    ProgrammedShareDto findOrNotFound(@Valid ProgrammedShareByIdFinderDto finderDto);

    @Valid
    ProgrammedShareDto create(@Valid ProgrammedShareCreateDto createDto);

    @Valid
    ProgrammedShareDto update(@Valid ProgrammedShareUpdateDto updateDto);

    void delete(@Valid ProgrammedShareDeleteDto deleteDto);
}
