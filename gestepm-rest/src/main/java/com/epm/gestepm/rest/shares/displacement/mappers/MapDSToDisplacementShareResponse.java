package com.epm.gestepm.rest.shares.displacement.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.displacement.dto.DisplacementShareDto;
import com.epm.gestepm.restapi.openapi.model.DisplacementShare;
import org.mapstruct.*;

import java.util.List;

@Mapper
public interface MapDSToDisplacementShareResponse {

  @Mapping(source = "userId", target = "user.id")
  @Mapping(source = "projectId", target = "project.id")
  @Mapping(source = "createdBy", target = "createdBy.id")
  @Mapping(source = "updatedBy", target = "updatedBy.id")
  DisplacementShare from(DisplacementShareDto dto);

  List<DisplacementShare> from(Page<DisplacementShareDto> list);

}
