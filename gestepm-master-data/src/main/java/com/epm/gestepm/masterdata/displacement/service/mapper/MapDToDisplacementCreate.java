package com.epm.gestepm.masterdata.displacement.service.mapper;

import com.epm.gestepm.masterdata.api.displacement.dto.creator.DisplacementCreateDto;
import com.epm.gestepm.masterdata.displacement.dao.entity.creator.DisplacementCreate;
import org.mapstruct.Mapper;

@Mapper
public interface MapDToDisplacementCreate {

  DisplacementCreate from(DisplacementCreateDto createDto);

}
