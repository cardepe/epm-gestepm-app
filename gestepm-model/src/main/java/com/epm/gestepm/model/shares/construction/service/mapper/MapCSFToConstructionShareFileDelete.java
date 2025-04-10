package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.deleter.ConstructionShareFileDelete;
import com.epm.gestepm.modelapi.shares.construction.dto.deleter.ConstructionShareFileDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSFToConstructionShareFileDelete {

  ConstructionShareFileDelete from(ConstructionShareFileDeleteDto deleteDto);

}
