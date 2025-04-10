package com.epm.gestepm.rest.shares.displacement.mappers;

import com.epm.gestepm.modelapi.shares.displacement.dto.creator.DisplacementShareCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateDisplacementShareV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapDIToDisplacementShareCreateDto {

  DisplacementShareCreateDto from(CreateDisplacementShareV1Request req);

}
