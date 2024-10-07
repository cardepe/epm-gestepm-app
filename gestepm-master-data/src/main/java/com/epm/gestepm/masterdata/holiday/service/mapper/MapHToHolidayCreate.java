package com.epm.gestepm.masterdata.holiday.service.mapper;

import com.epm.gestepm.masterdata.api.holiday.dto.creator.HolidayCreateDto;
import com.epm.gestepm.masterdata.holiday.dao.entity.creator.HolidayCreate;
import org.mapstruct.Mapper;

@Mapper
public interface MapHToHolidayCreate {

  HolidayCreate from(HolidayCreateDto createDto);

}
