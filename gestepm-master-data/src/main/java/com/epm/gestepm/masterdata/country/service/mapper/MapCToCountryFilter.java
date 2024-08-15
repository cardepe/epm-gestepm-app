package com.epm.gestepm.masterdata.country.service.mapper;

import com.epm.gestepm.masterdata.api.country.dto.filter.CountryFilterDto;
import com.epm.gestepm.masterdata.country.dao.entity.filter.CountryFilter;
import org.mapstruct.Mapper;

@Mapper
public interface MapCToCountryFilter {

  CountryFilter from(CountryFilterDto filterDto);

}
