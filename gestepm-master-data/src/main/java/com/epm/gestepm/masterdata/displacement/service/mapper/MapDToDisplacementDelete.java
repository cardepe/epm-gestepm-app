package com.epm.gestepm.masterdata.displacement.service.mapper;

import com.epm.gestepm.masterdata.api.displacement.dto.deleter.DisplacementDeleteDto;
import com.epm.gestepm.masterdata.displacement.dao.entity.deleter.DisplacementDelete;
import org.mapstruct.Mapper;

@Mapper
public interface MapDToDisplacementDelete {

  DisplacementDelete from(DisplacementDeleteDto deleteDto);

}
