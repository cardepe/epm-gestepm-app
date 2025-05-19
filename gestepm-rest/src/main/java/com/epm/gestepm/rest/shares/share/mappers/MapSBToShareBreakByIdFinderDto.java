package com.epm.gestepm.rest.shares.share.mappers;

import com.epm.gestepm.modelapi.shares.breaks.dto.filter.ShareBreakFilterDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.finder.ShareBreakByIdFinderDto;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareBreakFindRestRequest;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareBreakListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapSBToShareBreakByIdFinderDto {

    ShareBreakByIdFinderDto from(ConstructionShareBreakFindRestRequest req);

}
