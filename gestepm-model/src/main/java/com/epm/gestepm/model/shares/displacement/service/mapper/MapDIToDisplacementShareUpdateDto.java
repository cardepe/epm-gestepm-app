package com.epm.gestepm.model.shares.displacement.service.mapper;

import com.epm.gestepm.model.shares.displacement.dao.entity.updater.DisplacementShareUpdate;
import com.epm.gestepm.modelapi.shares.displacement.dto.updater.DisplacementShareUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapDIToDisplacementShareUpdateDto {

  DisplacementShareUpdateDto from(DisplacementShareUpdate updateDto);

}
