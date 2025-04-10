package com.epm.gestepm.rest.shares.displacement.mappers;

import com.epm.gestepm.modelapi.shares.displacement.dto.filter.DisplacementShareFilterDto;
import com.epm.gestepm.rest.shares.displacement.request.DisplacementShareListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapDSToDisplacementShareFilterDto {

  DisplacementShareFilterDto from(DisplacementShareListRestRequest req);

}
