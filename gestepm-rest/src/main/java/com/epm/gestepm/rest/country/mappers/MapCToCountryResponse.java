package com.epm.gestepm.rest.country.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.country.dto.CountryDto;
import com.epm.gestepm.restapi.openapi.model.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapCToCountryResponse {

  Country from(CountryDto dto);

  List<Country> from(Page<CountryDto> list);

}
