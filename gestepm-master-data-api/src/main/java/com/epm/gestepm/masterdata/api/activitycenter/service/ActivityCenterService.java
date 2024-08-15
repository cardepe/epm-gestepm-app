package com.epm.gestepm.masterdata.api.activitycenter.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.creator.ActivityCenterCreateDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.deleter.ActivityCenterDeleteDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.filter.ActivityCenterFilterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.finder.ActivityCenterByIdFinderDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.updater.ActivityCenterUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ActivityCenterService {

  List<@Valid ActivityCenterDto> list(@Valid ActivityCenterFilterDto filterDto);

  Page<@Valid ActivityCenterDto> list(@Valid ActivityCenterFilterDto filterDto, Long offset, Long limit);

  Optional<@Valid ActivityCenterDto> find(@Valid ActivityCenterByIdFinderDto finderDto);

  @Valid
  ActivityCenterDto findOrNotFound(@Valid ActivityCenterByIdFinderDto finderDto);

  @Valid
  ActivityCenterDto create(@Valid ActivityCenterCreateDto createDto);

  @Valid
  ActivityCenterDto update(@Valid ActivityCenterUpdateDto updateDto);

  void delete(@Valid ActivityCenterDeleteDto deleteDto);

}
