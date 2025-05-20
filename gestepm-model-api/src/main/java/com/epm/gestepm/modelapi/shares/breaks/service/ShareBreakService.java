package com.epm.gestepm.modelapi.shares.breaks.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.breaks.dto.ShareBreakDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.creator.ShareBreakCreateDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.deleter.ShareBreakDeleteDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.filter.ShareBreakFilterDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.finder.ShareBreakByIdFinderDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.updater.ShareBreakUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ShareBreakService {

    List<@Valid ShareBreakDto> list(@Valid ShareBreakFilterDto filterDto);

    Page<@Valid ShareBreakDto> list(@Valid ShareBreakFilterDto filterDto, Long offset, Long limit);
    
    Optional<@Valid ShareBreakDto> find(@Valid ShareBreakByIdFinderDto finderDto);

    @Valid
    ShareBreakDto findOrNotFound(@Valid ShareBreakByIdFinderDto finderDto);

    @Valid
    ShareBreakDto create(@Valid ShareBreakCreateDto createDto);

    @Valid
    ShareBreakDto update(@Valid ShareBreakUpdateDto updateDto);

    void delete(@Valid ShareBreakDeleteDto deleteDto);
}
