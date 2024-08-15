package com.epm.gestepm.masterdata.country.service.mapper;

import com.epm.gestepm.masterdata.api.country.dto.deleter.CountryDeleteDto;
import com.epm.gestepm.masterdata.country.dao.entity.deleter.CountryDelete;
import org.mapstruct.Mapper;

@Mapper
public interface MapCToCountryDelete {

  CountryDelete from(CountryDeleteDto deleteDto);

}
