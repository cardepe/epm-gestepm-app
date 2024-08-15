package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareCreate;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSToNoProgrammedShareCreate {

  NoProgrammedShareCreate from(NoProgrammedShareCreateDto createDto);

}
