package com.epm.gestepm.rest.signings.teleworking.mappers;

import com.epm.gestepm.modelapi.signings.teleworking.dto.updater.TeleworkingSigningUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateTeleworkingSigningV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningUpdateDto {

  TeleworkingSigningUpdateDto from(UpdateTeleworkingSigningV1Request req);

}
