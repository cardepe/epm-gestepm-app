package com.epm.gestepm.rest.holiday.mappers;

import com.epm.gestepm.masterdata.api.holiday.dto.finder.HolidayByIdFinderDto;
import com.epm.gestepm.rest.holiday.request.HolidayFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapHRToHolidayByIdFinderDto {

  HolidayByIdFinderDto from(HolidayFindRestRequest req);

}
