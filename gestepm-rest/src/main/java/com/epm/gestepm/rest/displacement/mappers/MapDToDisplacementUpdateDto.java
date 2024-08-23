package com.epm.gestepm.rest.displacement.mappers;

import com.epm.gestepm.masterdata.api.displacement.dto.updater.DisplacementUpdateDto;
import com.epm.gestepm.restapi.openapi.model.ReqUpdateDisplacement;
import org.mapstruct.Mapper;

@Mapper
public interface MapDToDisplacementUpdateDto {

  DisplacementUpdateDto from(ReqUpdateDisplacement req);

}
