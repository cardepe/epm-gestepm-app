package com.epm.gestepm.rest.shares.share.mappers;

import com.epm.gestepm.modelapi.shares.breaks.dto.finder.ShareBreakByIdFinderDto;
import com.epm.gestepm.rest.inspection.request.InspectionBreakFindRestRequest;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareBreakFindRestRequest;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareBreakFindRestRequest;
import com.epm.gestepm.rest.shares.work.request.WorkShareBreakFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapSBToShareBreakByIdFinderDto {

    ShareBreakByIdFinderDto from(ConstructionShareBreakFindRestRequest req);

    ShareBreakByIdFinderDto from(InspectionBreakFindRestRequest req);

    ShareBreakByIdFinderDto from(ProgrammedShareBreakFindRestRequest req);

    ShareBreakByIdFinderDto from(WorkShareBreakFindRestRequest req);

}
