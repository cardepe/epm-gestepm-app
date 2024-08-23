package com.epm.gestepm.rest.displacement.mappers;

import com.epm.gestepm.masterdata.api.displacement.dto.finder.DisplacementByIdFinderDto;
import com.epm.gestepm.rest.displacement.request.DisplacementFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapDRToDByIdFinderDto {

  DisplacementByIdFinderDto from(DisplacementFindRestRequest req);

}
