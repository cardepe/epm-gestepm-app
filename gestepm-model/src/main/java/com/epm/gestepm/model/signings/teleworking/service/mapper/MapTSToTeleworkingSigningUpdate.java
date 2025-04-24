package com.epm.gestepm.model.signings.teleworking.service.mapper;

import com.epm.gestepm.model.signings.teleworking.dao.entity.updater.TeleworkingSigningUpdate;
import com.epm.gestepm.modelapi.signings.teleworking.dto.TeleworkingSigningDto;
import com.epm.gestepm.modelapi.signings.teleworking.dto.updater.TeleworkingSigningUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapTSToTeleworkingSigningUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  TeleworkingSigningUpdate from(TeleworkingSigningUpdateDto source, @MappingTarget TeleworkingSigningUpdate target);

  TeleworkingSigningUpdate from(TeleworkingSigningUpdateDto updateDto);

  TeleworkingSigningUpdate from(TeleworkingSigningDto dto);

}
