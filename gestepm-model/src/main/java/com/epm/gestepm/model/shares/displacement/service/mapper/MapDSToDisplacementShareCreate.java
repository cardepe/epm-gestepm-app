package com.epm.gestepm.model.shares.displacement.service.mapper;

import com.epm.gestepm.model.shares.displacement.dao.entity.creator.DisplacementShareCreate;
import com.epm.gestepm.modelapi.shares.displacement.dto.creator.DisplacementShareCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapDSToDisplacementShareCreate {

  DisplacementShareCreate from(DisplacementShareCreateDto createDto);

}
