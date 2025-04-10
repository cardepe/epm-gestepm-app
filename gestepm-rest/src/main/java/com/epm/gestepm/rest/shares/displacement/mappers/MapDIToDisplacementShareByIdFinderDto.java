package com.epm.gestepm.rest.shares.displacement.mappers;

import com.epm.gestepm.modelapi.shares.displacement.dto.finder.DisplacementShareByIdFinderDto;
import com.epm.gestepm.rest.shares.displacement.request.DisplacementShareFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapDIToDisplacementShareByIdFinderDto {

  DisplacementShareByIdFinderDto from(DisplacementShareFindRestRequest req);

}
