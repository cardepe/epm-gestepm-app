package com.epm.gestepm.rest.personalexpense.mappers;

import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFilterDto;
import com.epm.gestepm.rest.personalexpense.request.PersonalExpenseListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEToPersonalExpenseFilterDto {

  PersonalExpenseFilterDto from(PersonalExpenseListRestRequest req);

}
