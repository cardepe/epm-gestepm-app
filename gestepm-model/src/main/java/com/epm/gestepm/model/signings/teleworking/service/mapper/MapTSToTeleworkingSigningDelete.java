package com.epm.gestepm.model.signings.teleworking.service.mapper;

import com.epm.gestepm.model.signings.teleworking.dao.entity.deleter.TeleworkingSigningDelete;
import com.epm.gestepm.modelapi.signings.teleworking.dto.deleter.TeleworkingSigningDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningDelete {

  TeleworkingSigningDelete from(TeleworkingSigningDeleteDto deleteDto);

}
