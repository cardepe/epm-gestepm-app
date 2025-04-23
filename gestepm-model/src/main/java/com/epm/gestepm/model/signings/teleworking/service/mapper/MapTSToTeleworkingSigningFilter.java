package com.epm.gestepm.model.signings.teleworking.service.mapper;

import com.epm.gestepm.model.signings.teleworking.dao.entity.filter.TeleworkingSigningFilter;
import com.epm.gestepm.modelapi.signings.teleworking.dto.filter.TeleworkingSigningFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningFilter {

  TeleworkingSigningFilter from(TeleworkingSigningFilterDto filterDto);

}
