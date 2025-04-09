package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.deleter.ConstructionShareDelete;
import com.epm.gestepm.modelapi.shares.construction.dto.deleter.ConstructionShareDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSToConstructionShareDelete {

  ConstructionShareDelete from(ConstructionShareDeleteDto deleteDto);

}
