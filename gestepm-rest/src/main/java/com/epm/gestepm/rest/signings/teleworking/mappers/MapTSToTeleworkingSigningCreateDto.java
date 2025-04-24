package com.epm.gestepm.rest.signings.teleworking.mappers;

import com.epm.gestepm.modelapi.signings.teleworking.dto.creator.TeleworkingSigningCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateTeleworkingSigningV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningCreateDto {

  TeleworkingSigningCreateDto from(CreateTeleworkingSigningV1Request req);

}
