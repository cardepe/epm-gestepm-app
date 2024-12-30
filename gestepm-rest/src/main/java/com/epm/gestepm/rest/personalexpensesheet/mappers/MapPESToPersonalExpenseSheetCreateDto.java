package com.epm.gestepm.rest.personalexpensesheet.mappers;

import com.epm.gestepm.modelapi.personalexpensesheet.dto.creator.PersonalExpenseSheetCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreatePersonalExpenseSheetV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPESToPersonalExpenseSheetCreateDto {

  PersonalExpenseSheetCreateDto from(CreatePersonalExpenseSheetV1Request req);

}
