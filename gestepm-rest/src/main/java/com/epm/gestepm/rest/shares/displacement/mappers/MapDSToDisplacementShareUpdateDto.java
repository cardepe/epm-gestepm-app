package com.epm.gestepm.rest.shares.displacement.mappers;

import com.epm.gestepm.modelapi.shares.displacement.dto.updater.DisplacementShareUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateDisplacementShareV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapDSToDisplacementShareUpdateDto {

  DisplacementShareUpdateDto from(UpdateDisplacementShareV1Request req);

}
