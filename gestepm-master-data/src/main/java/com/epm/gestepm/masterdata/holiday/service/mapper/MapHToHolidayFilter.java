package com.epm.gestepm.masterdata.holiday.service.mapper;

import com.epm.gestepm.masterdata.api.holiday.dto.filter.HolidayFilterDto;
import com.epm.gestepm.masterdata.holiday.dao.entity.filter.HolidayFilter;
import org.mapstruct.Mapper;

@Mapper
public interface MapHToHolidayFilter {

  HolidayFilter from(HolidayFilterDto filterDto);

}
