package com.epm.gestepm.rest.country.mappers;

import com.epm.gestepm.rest.country.request.CountryListRestRequest;
import com.epm.gestepm.masterdata.api.country.dto.filter.CountryFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCRToCountryFilterDto {

  CountryFilterDto from(CountryListRestRequest req);

}
