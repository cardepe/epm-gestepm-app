package com.epm.gestepm.rest.displacement.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.displacement.dto.DisplacementDto;
import com.epm.gestepm.restapi.openapi.model.Displacement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapDToDisplacementResponse {

  @Mapping(source = "activityCenterId", target = "activityCenter.id")
  Displacement from(DisplacementDto dto);

  List<Displacement> from(Page<DisplacementDto> list);

}
