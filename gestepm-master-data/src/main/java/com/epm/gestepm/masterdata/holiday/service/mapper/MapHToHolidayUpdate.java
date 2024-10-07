package com.epm.gestepm.masterdata.holiday.service.mapper;

import com.epm.gestepm.masterdata.api.holiday.dto.updater.HolidayUpdateDto;
import com.epm.gestepm.masterdata.holiday.dao.entity.updater.HolidayUpdate;
import org.mapstruct.Mapper;

@Mapper
public interface MapHToHolidayUpdate {

  HolidayUpdate from(HolidayUpdateDto updateDto);

}
