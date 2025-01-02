package com.epm.gestepm.rest.personalexpense.mappers;

import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreatePersonalExpenseV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEToPersonalExpenseCreateDto {

  PersonalExpenseCreateDto from(CreatePersonalExpenseV1Request req);

}
