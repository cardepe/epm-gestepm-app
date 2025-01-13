package com.epm.gestepm.modelapi.shares.noprogrammed.service;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareFileDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter.NoProgrammedShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareFileByIdFinderDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface NoProgrammedShareFileService {

    List<@Valid NoProgrammedShareFileDto> list(@Valid NoProgrammedShareFileFilterDto filterDto);
    
    Optional<@Valid NoProgrammedShareFileDto> find(@Valid NoProgrammedShareFileByIdFinderDto finderDto);

    @Valid
    NoProgrammedShareFileDto findOrNotFound(@Valid NoProgrammedShareFileByIdFinderDto finderDto);

    @Valid
    NoProgrammedShareFileDto create(@Valid NoProgrammedShareFileCreateDto createDto);

    void delete(@Valid NoProgrammedShareFileDeleteDto deleteDto);
    
}
