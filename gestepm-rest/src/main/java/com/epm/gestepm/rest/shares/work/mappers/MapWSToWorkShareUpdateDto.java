package com.epm.gestepm.rest.shares.work.mappers;

import com.epm.gestepm.modelapi.shares.work.dto.updater.WorkShareUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateWorkShareV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSToWorkShareUpdateDto {

  WorkShareUpdateDto from(UpdateWorkShareV1Request req);

}
