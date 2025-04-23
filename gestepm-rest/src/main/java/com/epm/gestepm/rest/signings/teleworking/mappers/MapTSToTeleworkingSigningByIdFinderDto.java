package com.epm.gestepm.rest.signings.teleworking.mappers;

import com.epm.gestepm.modelapi.signings.teleworking.dto.finder.TeleworkingSigningByIdFinderDto;
import com.epm.gestepm.rest.signings.teleworking.request.TeleworkingSigningFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningByIdFinderDto {

  TeleworkingSigningByIdFinderDto from(TeleworkingSigningFindRestRequest req);

}
