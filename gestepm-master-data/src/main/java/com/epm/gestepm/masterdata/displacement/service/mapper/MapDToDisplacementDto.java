package com.epm.gestepm.masterdata.displacement.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.displacement.dto.DisplacementDto;
import com.epm.gestepm.masterdata.displacement.dao.entity.Displacement;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapDToDisplacementDto {

  DisplacementDto from(Displacement displacement);

  List<DisplacementDto> from(List<Displacement> displacement);

  default Page<DisplacementDto> from(Page<Displacement> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

}
