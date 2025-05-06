package com.epm.gestepm.rest.shares.work.mappers;

import com.epm.gestepm.modelapi.shares.work.dto.finder.WorkShareByIdFinderDto;
import com.epm.gestepm.rest.shares.work.request.WorkShareFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSToWorkShareByIdFinderDto {

  WorkShareByIdFinderDto from(WorkShareFindRestRequest req);

}
