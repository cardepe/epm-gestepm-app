package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter.NoProgrammedShareFileDelete;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter.NoProgrammedShareFileDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSFToNoProgrammedShareFileDelete {

  NoProgrammedShareFileDelete from(NoProgrammedShareFileDeleteDto deleteDto);

}
