package com.epm.gestepm.rest.personalexpensesheet.mappers;

import com.epm.gestepm.modelapi.personalexpensesheet.dto.updater.PersonalExpenseSheetUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdatePersonalExpenseSheetV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPESToPersonalExpenseSheetUpdateDto {

  PersonalExpenseSheetUpdateDto from(UpdatePersonalExpenseSheetV1Request req);

}
