package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter.NoProgrammedShareDelete;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter.NoProgrammedShareDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSToNoProgrammedShareDelete {

  NoProgrammedShareDelete from(NoProgrammedShareDeleteDto deleteDto);

}
