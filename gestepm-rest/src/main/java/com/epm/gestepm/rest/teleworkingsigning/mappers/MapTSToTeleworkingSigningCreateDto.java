package com.epm.gestepm.rest.teleworkingsigning.mappers;

import com.epm.gestepm.modelapi.teleworkingsigning.dto.creator.TeleworkingSigningCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateTeleworkingSigningV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningCreateDto {

  TeleworkingSigningCreateDto from(CreateTeleworkingSigningV1Request req);

}
