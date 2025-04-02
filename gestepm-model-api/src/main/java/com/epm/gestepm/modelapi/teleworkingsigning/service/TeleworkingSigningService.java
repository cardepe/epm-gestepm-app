package com.epm.gestepm.modelapi.teleworkingsigning.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.TeleworkingSigningDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.creator.TeleworkingSigningCreateDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.deleter.TeleworkingSigningDeleteDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.filter.TeleworkingSigningFilterDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.finder.TeleworkingSigningByIdFinderDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.updater.TeleworkingSigningUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface TeleworkingSigningService {

  List<@Valid TeleworkingSigningDto> list(@Valid TeleworkingSigningFilterDto filterDto);

  Page<@Valid TeleworkingSigningDto> list(@Valid TeleworkingSigningFilterDto filterDto, Long offset, Long limit);

  Optional<@Valid TeleworkingSigningDto> find(@Valid TeleworkingSigningByIdFinderDto finderDto);

  @Valid
  TeleworkingSigningDto findOrNotFound(@Valid TeleworkingSigningByIdFinderDto finderDto);

  @Valid
  TeleworkingSigningDto create(@Valid TeleworkingSigningCreateDto createDto);

  @Valid
  TeleworkingSigningDto update(@Valid TeleworkingSigningUpdateDto updateDto);

  void delete(@Valid TeleworkingSigningDeleteDto deleteDto);

}
