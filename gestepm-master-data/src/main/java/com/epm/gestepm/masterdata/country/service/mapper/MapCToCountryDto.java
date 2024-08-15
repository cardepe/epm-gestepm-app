package com.epm.gestepm.masterdata.country.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.country.dto.CountryDto;
import com.epm.gestepm.masterdata.country.dao.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapCToCountryDto {

  CountryDto from(Country country);

  List<CountryDto> from(List<Country> country);

  default Page<CountryDto> from(Page<Country> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

}
