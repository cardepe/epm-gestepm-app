package com.epm.gestepm.rest.displacement.mappers;

import com.epm.gestepm.masterdata.api.displacement.dto.creator.DisplacementCreateDto;
import com.epm.gestepm.restapi.openapi.model.ReqCreateDisplacement;
import org.mapstruct.Mapper;

@Mapper
public interface MapDToDisplacementCreateDto {

  DisplacementCreateDto from(ReqCreateDisplacement req);

}
