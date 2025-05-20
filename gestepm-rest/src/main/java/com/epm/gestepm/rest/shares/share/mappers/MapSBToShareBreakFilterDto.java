package com.epm.gestepm.rest.shares.share.mappers;

import com.epm.gestepm.modelapi.shares.breaks.dto.filter.ShareBreakFilterDto;
import com.epm.gestepm.rest.inspection.request.InspectionBreakListRestRequest;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareBreakListRestRequest;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareBreakListRestRequest;
import com.epm.gestepm.rest.shares.work.request.WorkShareBreakListRestRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper
public interface MapSBToShareBreakFilterDto {

    @Mapping(target = "constructionShareIds", expression = "java(toList(req.getConstructionShareId()))")
    ShareBreakFilterDto from(ConstructionShareBreakListRestRequest req);

    @Mapping(target = "inspectionIds", expression = "java(toList(req.getInspectionId()))")
    ShareBreakFilterDto from(InspectionBreakListRestRequest req);

    @Mapping(target = "programmedShareIds", expression = "java(toList(req.getProgrammedShareId()))")
    ShareBreakFilterDto from(ProgrammedShareBreakListRestRequest req);

    @Mapping(target = "workShareIds", expression = "java(toList(req.getWorkShareId()))")
    ShareBreakFilterDto from(WorkShareBreakListRestRequest req);

    default List<Integer> toList(Integer value) {
        return value == null ? Collections.emptyList() : Collections.singletonList(value);
    }
}
