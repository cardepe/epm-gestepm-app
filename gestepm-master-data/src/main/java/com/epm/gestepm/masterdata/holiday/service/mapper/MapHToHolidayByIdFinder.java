package com.epm.gestepm.masterdata.holiday.service.mapper;

import com.epm.gestepm.masterdata.api.holiday.dto.finder.HolidayByIdFinderDto;
import com.epm.gestepm.masterdata.holiday.dao.entity.finder.HolidayByIdFinder;
import org.mapstruct.Mapper;

@Mapper
public interface MapHToHolidayByIdFinder {

  HolidayByIdFinder from(HolidayByIdFinderDto finderDto);

}
