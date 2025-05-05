package com.epm.gestepm.rest.shares.work.mappers;

import com.epm.gestepm.modelapi.shares.work.dto.creator.WorkShareCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateWorkShareV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSToWorkShareCreateDto {

  WorkShareCreateDto from(CreateWorkShareV1Request req);

}
