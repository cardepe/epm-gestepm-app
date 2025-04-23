package com.epm.gestepm.model.signings.teleworking.service.mapper;

import com.epm.gestepm.model.signings.teleworking.dao.entity.finder.TeleworkingSigningByIdFinder;
import com.epm.gestepm.modelapi.signings.teleworking.dto.finder.TeleworkingSigningByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningByIdFinder {

  TeleworkingSigningByIdFinder from(TeleworkingSigningByIdFinderDto finderDto);

}
