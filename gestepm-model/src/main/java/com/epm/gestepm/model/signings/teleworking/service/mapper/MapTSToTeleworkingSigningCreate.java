package com.epm.gestepm.model.signings.teleworking.service.mapper;

import com.epm.gestepm.model.signings.teleworking.dao.entity.creator.TeleworkingSigningCreate;
import com.epm.gestepm.modelapi.signings.teleworking.dto.creator.TeleworkingSigningCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningCreate {

  TeleworkingSigningCreate from(TeleworkingSigningCreateDto createDto);

}
