package com.epm.gestepm.modelapi.shares.programmed.service;

import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareFileDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.creator.ProgrammedShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.deleter.ProgrammedShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.finder.ProgrammedShareFileByIdFinderDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProgrammedShareFileService {

    List<@Valid ProgrammedShareFileDto> list(@Valid ProgrammedShareFileFilterDto filterDto);
    
    Optional<@Valid ProgrammedShareFileDto> find(@Valid ProgrammedShareFileByIdFinderDto finderDto);

    @Valid
    ProgrammedShareFileDto findOrNotFound(@Valid ProgrammedShareFileByIdFinderDto finderDto);

    @Valid
    ProgrammedShareFileDto create(@Valid ProgrammedShareFileCreateDto createDto);

    void delete(@Valid ProgrammedShareFileDeleteDto deleteDto);
    
}
