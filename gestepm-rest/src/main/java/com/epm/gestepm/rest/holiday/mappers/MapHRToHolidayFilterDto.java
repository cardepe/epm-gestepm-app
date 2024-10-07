package com.epm.gestepm.rest.holiday.mappers;

import com.epm.gestepm.masterdata.api.holiday.dto.filter.HolidayFilterDto;
import com.epm.gestepm.rest.holiday.request.HolidayListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapHRToHolidayFilterDto {

    HolidayFilterDto from(HolidayListRestRequest req);
    
}
