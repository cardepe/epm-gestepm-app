package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareUpdate;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSToConstructionShareUpdateDto {

  ConstructionShareUpdateDto from(ConstructionShareUpdate updateDto);

}
