package com.epm.gestepm.modelapi.shares.noprogrammed.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareCreateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter.NoProgrammedShareDeleteDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFilterDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;

import javax.validation.Valid;
import java.util.Optional;

public interface NoProgrammedShareService {

    Page<@Valid NoProgrammedShareDto> list(@Valid NoProgrammedShareFilterDto filterDto, Long offset, Long limit);
    
    Optional<@Valid NoProgrammedShareDto> find(@Valid NoProgrammedShareByIdFinderDto finderDto);

    @Valid
    NoProgrammedShareDto findOrNotFound(@Valid NoProgrammedShareByIdFinderDto finderDto);

    @Valid
    NoProgrammedShareDto create(@Valid NoProgrammedShareCreateDto createDto);

    @Valid
    NoProgrammedShareDto update(@Valid NoProgrammedShareUpdateDto updateDto);

    void delete(@Valid NoProgrammedShareDeleteDto deleteDto);
}
