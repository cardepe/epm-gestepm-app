package com.epm.gestepm.rest.shares.share.mappers;

import com.epm.gestepm.modelapi.shares.breaks.dto.filter.ShareBreakFilterDto;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareBreakListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapSBToShareBreakFilterDto {

    ShareBreakFilterDto from(ConstructionShareBreakListRestRequest req);

}
