package com.epm.gestepm.model.shares.displacement.service.mapper;

import com.epm.gestepm.model.shares.displacement.dao.entity.deleter.DisplacementShareDelete;
import com.epm.gestepm.modelapi.shares.displacement.dto.deleter.DisplacementShareDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapDSToDisplacementShareDelete {

  DisplacementShareDelete from(DisplacementShareDeleteDto deleteDto);

}
