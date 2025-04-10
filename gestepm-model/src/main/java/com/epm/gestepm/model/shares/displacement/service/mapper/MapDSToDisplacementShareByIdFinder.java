package com.epm.gestepm.model.shares.displacement.service.mapper;

import com.epm.gestepm.model.shares.displacement.dao.entity.finder.DisplacementShareByIdFinder;
import com.epm.gestepm.modelapi.shares.displacement.dto.finder.DisplacementShareByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapDSToDisplacementShareByIdFinder {

  DisplacementShareByIdFinder from(DisplacementShareByIdFinderDto finderDto);

}
