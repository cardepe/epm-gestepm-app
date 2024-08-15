package com.epm.gestepm.masterdata.country.service.mapper;

import com.epm.gestepm.masterdata.api.country.dto.creator.CountryCreateDto;
import com.epm.gestepm.masterdata.country.dao.entity.creator.CountryCreate;
import org.mapstruct.Mapper;

@Mapper
public interface MapCToCountryCreate {

  CountryCreate from(CountryCreateDto createDto);

}
