package com.epm.gestepm.model.teleworkingsigning.service.mapper;

import com.epm.gestepm.model.teleworkingsigning.dao.entity.finder.TeleworkingSigningByIdFinder;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.finder.TeleworkingSigningByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningByIdFinder {

  TeleworkingSigningByIdFinder from(TeleworkingSigningByIdFinderDto finderDto);

}
