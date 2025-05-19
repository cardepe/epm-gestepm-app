package com.epm.gestepm.model.shares.breaks.service.mapper;

import com.epm.gestepm.model.shares.breaks.dao.entity.updater.ShareBreakUpdate;
import com.epm.gestepm.modelapi.shares.breaks.dto.ShareBreakDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.updater.ShareBreakUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapSBToShareBreakUpdateDto {

  ShareBreakUpdateDto from(ShareBreakUpdate updateDto);

  ShareBreakUpdateDto from(ShareBreakDto dto);

}
