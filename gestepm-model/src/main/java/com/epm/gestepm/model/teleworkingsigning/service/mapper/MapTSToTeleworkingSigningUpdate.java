package com.epm.gestepm.model.teleworkingsigning.service.mapper;

import com.epm.gestepm.model.teleworkingsigning.dao.entity.updater.TeleworkingSigningUpdate;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.TeleworkingSigningDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.updater.TeleworkingSigningUpdateDto;
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
