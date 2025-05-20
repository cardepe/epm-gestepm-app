package com.epm.gestepm.rest.shares.share.mappers;

import com.epm.gestepm.modelapi.shares.breaks.dto.updater.ShareBreakUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateConstructionShareBreakV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapSBToShareBreakUpdateDto {

    ShareBreakUpdateDto from(UpdateConstructionShareBreakV1Request req);

}
