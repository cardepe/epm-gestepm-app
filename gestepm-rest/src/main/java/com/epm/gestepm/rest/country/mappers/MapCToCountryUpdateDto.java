package com.epm.gestepm.rest.country.mappers;

import com.epm.gestepm.masterdata.api.country.dto.updater.CountryUpdateDto;
import com.epm.gestepm.restapi.openapi.model.ReqUpdateCountry;
import org.mapstruct.Mapper;

@Mapper
public interface MapCToCountryUpdateDto {

  CountryUpdateDto from(ReqUpdateCountry req);

}
