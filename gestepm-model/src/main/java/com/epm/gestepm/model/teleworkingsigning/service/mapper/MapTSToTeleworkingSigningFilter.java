package com.epm.gestepm.model.teleworkingsigning.service.mapper;

import com.epm.gestepm.model.teleworkingsigning.dao.entity.filter.TeleworkingSigningFilter;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.filter.TeleworkingSigningFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningFilter {

  TeleworkingSigningFilter from(TeleworkingSigningFilterDto filterDto);

}
