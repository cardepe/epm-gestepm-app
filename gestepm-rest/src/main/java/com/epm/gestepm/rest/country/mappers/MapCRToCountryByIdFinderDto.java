package com.epm.gestepm.rest.country.mappers;

import com.epm.gestepm.rest.country.request.CountryFindRestRequest;
import com.epm.gestepm.masterdata.api.country.dto.finder.CountryByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCRToCountryByIdFinderDto {

  CountryByIdFinderDto from(CountryFindRestRequest req);

}
