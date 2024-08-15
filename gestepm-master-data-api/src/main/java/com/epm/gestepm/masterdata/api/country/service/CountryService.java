package com.epm.gestepm.masterdata.api.country.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.country.dto.CountryDto;
import com.epm.gestepm.masterdata.api.country.dto.creator.CountryCreateDto;
import com.epm.gestepm.masterdata.api.country.dto.deleter.CountryDeleteDto;
import com.epm.gestepm.masterdata.api.country.dto.filter.CountryFilterDto;
import com.epm.gestepm.masterdata.api.country.dto.finder.CountryByIdFinderDto;
import com.epm.gestepm.masterdata.api.country.dto.updater.CountryUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface CountryService {

  List<@Valid CountryDto> list(@Valid CountryFilterDto filterDto);

  Page<@Valid CountryDto> list(@Valid CountryFilterDto filterDto, Long offset, Long limit);

  Optional<@Valid CountryDto> find(@Valid CountryByIdFinderDto finderDto);

  @Valid
  CountryDto findOrNotFound(@Valid CountryByIdFinderDto finderDto);

  @Valid
  CountryDto create(@Valid CountryCreateDto createDto);

  @Valid
  CountryDto update(@Valid CountryUpdateDto updateDto);

  void delete(@Valid CountryDeleteDto deleteDto);

}
