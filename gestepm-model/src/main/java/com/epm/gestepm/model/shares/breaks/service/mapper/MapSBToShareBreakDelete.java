package com.epm.gestepm.model.shares.breaks.service.mapper;

import com.epm.gestepm.model.shares.breaks.dao.entity.deleter.ShareBreakDelete;
import com.epm.gestepm.modelapi.shares.breaks.dto.deleter.ShareBreakDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapSBToShareBreakDelete {

  ShareBreakDelete from(ShareBreakDeleteDto deleteDto);

}
