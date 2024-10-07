package com.epm.gestepm.masterdata.api.holiday.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.holiday.dto.HolidayDto;
import com.epm.gestepm.masterdata.api.holiday.dto.creator.HolidayCreateDto;
import com.epm.gestepm.masterdata.api.holiday.dto.deleter.HolidayDeleteDto;
import com.epm.gestepm.masterdata.api.holiday.dto.filter.HolidayFilterDto;
import com.epm.gestepm.masterdata.api.holiday.dto.finder.HolidayByIdFinderDto;
import com.epm.gestepm.masterdata.api.holiday.dto.updater.HolidayUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface HolidayService {

  List<@Valid HolidayDto> list(@Valid HolidayFilterDto filterDto);

  Page<@Valid HolidayDto> list(@Valid HolidayFilterDto filterDto, Long offset, Long limit);

  Optional<@Valid HolidayDto> find(@Valid HolidayByIdFinderDto finderDto);

  @Valid
  HolidayDto findOrNotFound(@Valid HolidayByIdFinderDto finderDto);

  @Valid
  HolidayDto create(@Valid HolidayCreateDto createDto);

  @Valid
  HolidayDto update(@Valid HolidayUpdateDto updateDto);

  void delete(@Valid HolidayDeleteDto deleteDto);

}
