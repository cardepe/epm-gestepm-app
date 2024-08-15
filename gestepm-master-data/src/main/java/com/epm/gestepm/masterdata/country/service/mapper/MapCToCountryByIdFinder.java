package com.epm.gestepm.masterdata.country.service.mapper;

import com.epm.gestepm.masterdata.api.country.dto.finder.CountryByIdFinderDto;
import com.epm.gestepm.masterdata.country.dao.entity.finder.CountryByIdFinder;
import org.mapstruct.Mapper;

@Mapper
public interface MapCToCountryByIdFinder {

  CountryByIdFinder from(CountryByIdFinderDto finderDto);

}
