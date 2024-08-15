package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareUpdate;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSToNoProgrammedShareUpdate {

  NoProgrammedShareUpdate from(NoProgrammedShareUpdateDto updateDto);

}
