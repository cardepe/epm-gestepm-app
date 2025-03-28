package com.epm.gestepm.rest.teleworkingsigning.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.TeleworkingSigningDto;
import com.epm.gestepm.restapi.openapi.model.PersonalExpense;
import com.epm.gestepm.restapi.openapi.model.TeleworkingSigning;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface MapTSToTeleworkingSigningResponse {

  @Mapping(source = "userId", target = "user.id")
  @Mapping(source = "projectId", target = "project.id")
  TeleworkingSigning from(TeleworkingSigningDto dto);

  List<TeleworkingSigning> from(Page<TeleworkingSigningDto> list);

}
