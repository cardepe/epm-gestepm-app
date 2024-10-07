package com.epm.gestepm.masterdata.holiday.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.holiday.dto.HolidayDto;
import com.epm.gestepm.masterdata.holiday.dao.entity.Holiday;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapHToHolidayDto {

  HolidayDto from(Holiday displacement);

  List<HolidayDto> from(List<Holiday> displacement);

  default Page<HolidayDto> from(Page<Holiday> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

}
