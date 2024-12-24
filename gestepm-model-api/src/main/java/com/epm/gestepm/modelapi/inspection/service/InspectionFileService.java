package com.epm.gestepm.modelapi.inspection.service;

import com.epm.gestepm.modelapi.inspection.dto.InspectionFileDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionFileCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFileFilterDto;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionFileByIdFinderDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface InspectionFileService {

    List<@Valid InspectionFileDto> list(@Valid InspectionFileFilterDto filterDto);
    
    Optional<@Valid InspectionFileDto> find(@Valid InspectionFileByIdFinderDto finderDto);

    @Valid
    InspectionFileDto findOrNotFound(@Valid InspectionFileByIdFinderDto finderDto);

    @Valid
    InspectionFileDto create(@Valid InspectionFileCreateDto createDto);
    
}
