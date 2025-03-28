package com.epm.gestepm.rest.teleworkingsigning.mappers;

import com.epm.gestepm.modelapi.teleworkingsigning.dto.finder.TeleworkingSigningByIdFinderDto;
import com.epm.gestepm.rest.teleworkingsigning.request.TeleworkingSigningFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningByIdFinderDto {

  TeleworkingSigningByIdFinderDto from(TeleworkingSigningFindRestRequest req);

}
