package com.epm.gestepm.rest.holiday.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.holiday.dto.HolidayDto;
import com.epm.gestepm.restapi.openapi.model.Holiday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapHToHolidayResponse {

  @Mapping(source = "countryId", target = "country.id")
  @Mapping(source = "activityCenterId", target = "activityCenter.id")
  Holiday from(HolidayDto dto);

  List<Holiday> from(Page<HolidayDto> list);

}
