package com.epm.gestepm.model.shares.breaks.service.mapper;

import com.epm.gestepm.model.shares.breaks.dao.entity.creator.ShareBreakCreate;
import com.epm.gestepm.modelapi.shares.breaks.dto.creator.ShareBreakCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapSBToShareBreakCreate {

  ShareBreakCreate from(ShareBreakCreateDto createDto);

}
