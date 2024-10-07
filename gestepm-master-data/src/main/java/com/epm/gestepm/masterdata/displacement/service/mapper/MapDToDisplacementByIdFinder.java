package com.epm.gestepm.masterdata.displacement.service.mapper;

import com.epm.gestepm.masterdata.api.displacement.dto.finder.DisplacementByIdFinderDto;
import com.epm.gestepm.masterdata.displacement.dao.entity.finder.DisplacementByIdFinder;
import org.mapstruct.Mapper;

@Mapper
public interface MapDToDisplacementByIdFinder {

  DisplacementByIdFinder from(DisplacementByIdFinderDto finderDto);

}
