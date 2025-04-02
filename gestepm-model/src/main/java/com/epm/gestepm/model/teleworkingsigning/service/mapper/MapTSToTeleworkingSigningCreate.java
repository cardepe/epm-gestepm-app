package com.epm.gestepm.model.teleworkingsigning.service.mapper;

import com.epm.gestepm.model.teleworkingsigning.dao.entity.creator.TeleworkingSigningCreate;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.creator.TeleworkingSigningCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningCreate {

  TeleworkingSigningCreate from(TeleworkingSigningCreateDto createDto);

}
