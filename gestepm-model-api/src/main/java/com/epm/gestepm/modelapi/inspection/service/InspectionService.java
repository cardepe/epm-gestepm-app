package com.epm.gestepm.modelapi.inspection.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.deleter.InspectionDeleteDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFilterDto;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionByIdFinderDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;

import javax.validation.Valid;
import java.util.Optional;

public interface InspectionService {

    Page<@Valid InspectionDto> list(@Valid InspectionFilterDto filterDto, Long offset, Long limit);
    
    Optional<@Valid InspectionDto> find(@Valid InspectionByIdFinderDto finderDto);

    @Valid
    InspectionDto findOrNotFound(@Valid InspectionByIdFinderDto finderDto);

    @Valid
    InspectionDto create(@Valid InspectionCreateDto createDto);

    @Valid
    InspectionDto update(@Valid InspectionUpdateDto updateDto);

    void delete(@Valid InspectionDeleteDto deleteDto);
}
