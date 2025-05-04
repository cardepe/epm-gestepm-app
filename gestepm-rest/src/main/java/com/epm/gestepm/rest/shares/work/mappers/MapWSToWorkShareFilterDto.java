package com.epm.gestepm.rest.shares.work.mappers;

import com.epm.gestepm.modelapi.shares.work.dto.filter.WorkShareFilterDto;
import com.epm.gestepm.rest.shares.work.request.WorkShareListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSToWorkShareFilterDto {

  WorkShareFilterDto from(WorkShareListRestRequest req);

}
