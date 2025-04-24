package com.epm.gestepm.rest.signings.teleworking.mappers;

import com.epm.gestepm.modelapi.signings.teleworking.dto.filter.TeleworkingSigningFilterDto;
import com.epm.gestepm.rest.signings.teleworking.request.TeleworkingSigningListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningFilterDto {

  TeleworkingSigningFilterDto from(TeleworkingSigningListRestRequest req);

}
