package com.epm.gestepm.rest.country.mappers;

import com.epm.gestepm.masterdata.api.country.dto.creator.CountryCreateDto;
import com.epm.gestepm.restapi.openapi.model.ReqCreateCountry;
import org.mapstruct.Mapper;

@Mapper
public interface MapCToCountryCreateDto {

  CountryCreateDto from(ReqCreateCountry req);

}
