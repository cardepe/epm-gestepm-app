package com.epm.gestepm.masterdata.country.service.mapper;

import com.epm.gestepm.masterdata.api.country.dto.updater.CountryUpdateDto;
import com.epm.gestepm.masterdata.country.dao.entity.updater.CountryUpdate;
import org.mapstruct.Mapper;

@Mapper
public interface MapCToCountryUpdate {

  CountryUpdate from(CountryUpdateDto updateDto);

}
