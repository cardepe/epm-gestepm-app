package com.epm.gestepm.model.shares.breaks.service.mapper;

import com.epm.gestepm.model.shares.breaks.dao.entity.finder.ShareBreakByIdFinder;
import com.epm.gestepm.modelapi.shares.breaks.dto.finder.ShareBreakByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapSBToShareBreakByIdFinder {

  ShareBreakByIdFinder from(ShareBreakByIdFinderDto finderDto);

}
