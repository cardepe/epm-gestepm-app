package com.epm.gestepm.masterdata.api.displacement.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.displacement.dto.DisplacementDto;
import com.epm.gestepm.masterdata.api.displacement.dto.creator.DisplacementCreateDto;
import com.epm.gestepm.masterdata.api.displacement.dto.deleter.DisplacementDeleteDto;
import com.epm.gestepm.masterdata.api.displacement.dto.filter.DisplacementFilterDto;
import com.epm.gestepm.masterdata.api.displacement.dto.finder.DisplacementByIdFinderDto;
import com.epm.gestepm.masterdata.api.displacement.dto.updater.DisplacementUpdateDto;

import javax.validation.Valid;
import java.util.Optional;

public interface DisplacementService {

  Page<@Valid DisplacementDto> list(@Valid DisplacementFilterDto filterDto, Long offset, Long limit);

  Optional<@Valid DisplacementDto> find(@Valid DisplacementByIdFinderDto finderDto);

  @Valid
  DisplacementDto findOrNotFound(@Valid DisplacementByIdFinderDto finderDto);

  @Valid
  DisplacementDto create(@Valid DisplacementCreateDto createDto);

  @Valid
  DisplacementDto update(@Valid DisplacementUpdateDto updateDto);

  void delete(@Valid DisplacementDeleteDto deleteDto);

}
