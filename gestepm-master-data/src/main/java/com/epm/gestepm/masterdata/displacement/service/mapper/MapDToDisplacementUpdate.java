package com.epm.gestepm.masterdata.displacement.service.mapper;

import com.epm.gestepm.masterdata.api.displacement.dto.updater.DisplacementUpdateDto;
import com.epm.gestepm.masterdata.displacement.dao.entity.updater.DisplacementUpdate;
import org.mapstruct.Mapper;

@Mapper
public interface MapDToDisplacementUpdate {

  DisplacementUpdate from(DisplacementUpdateDto updateDto);

}
