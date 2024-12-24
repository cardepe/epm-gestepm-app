package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShareStateEnum;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareStateEnumDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapNPSToNoProgrammedShareDto {

  NoProgrammedShareDto from(NoProgrammedShare country);

  List<NoProgrammedShareDto> from(List<NoProgrammedShare> country);

  default Page<NoProgrammedShareDto> from(Page<NoProgrammedShare> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

  NoProgrammedShareStateEnumDto from(NoProgrammedShareStateEnum stateEnum);
}
