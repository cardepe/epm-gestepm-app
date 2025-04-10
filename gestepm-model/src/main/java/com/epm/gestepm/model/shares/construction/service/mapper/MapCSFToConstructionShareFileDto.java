package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShareFile;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareFileDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapCSFToConstructionShareFileDto {

  ConstructionShareFileDto from(ConstructionShareFile file);

  List<ConstructionShareFileDto> from(List<ConstructionShareFile> files);

}
