package com.epm.gestepm.model.teleworkingsigning.service.mapper;

import com.epm.gestepm.model.teleworkingsigning.dao.entity.deleter.TeleworkingSigningDelete;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.deleter.TeleworkingSigningDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningDelete {

  TeleworkingSigningDelete from(TeleworkingSigningDeleteDto deleteDto);

}
