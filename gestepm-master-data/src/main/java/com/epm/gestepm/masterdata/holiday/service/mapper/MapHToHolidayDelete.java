package com.epm.gestepm.masterdata.holiday.service.mapper;

import com.epm.gestepm.masterdata.api.holiday.dto.deleter.HolidayDeleteDto;
import com.epm.gestepm.masterdata.holiday.dao.entity.deleter.HolidayDelete;
import org.mapstruct.Mapper;

@Mapper
public interface MapHToHolidayDelete {

  HolidayDelete from(HolidayDeleteDto deleteDto);

}
