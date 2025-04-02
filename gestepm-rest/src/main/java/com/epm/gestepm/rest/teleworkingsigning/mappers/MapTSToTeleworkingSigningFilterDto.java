package com.epm.gestepm.rest.teleworkingsigning.mappers;

import com.epm.gestepm.modelapi.teleworkingsigning.dto.filter.TeleworkingSigningFilterDto;
import com.epm.gestepm.rest.teleworkingsigning.request.TeleworkingSigningListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapTSToTeleworkingSigningFilterDto {

  TeleworkingSigningFilterDto from(TeleworkingSigningListRestRequest req);

}
