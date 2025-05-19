package com.epm.gestepm.rest.shares.share.mappers;

import com.epm.gestepm.modelapi.shares.breaks.dto.creator.ShareBreakCreateDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.filter.ShareBreakFilterDto;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareBreakListRestRequest;
import com.epm.gestepm.restapi.openapi.model.CreateConstructionShareBreakV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapSBToShareBreakCreateDto {

    ShareBreakCreateDto from(CreateConstructionShareBreakV1Request req);

}
