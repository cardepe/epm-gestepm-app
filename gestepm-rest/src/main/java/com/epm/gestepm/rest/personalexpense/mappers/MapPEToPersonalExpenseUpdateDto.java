package com.epm.gestepm.rest.personalexpense.mappers;

import com.epm.gestepm.modelapi.personalexpense.dto.updater.PersonalExpenseUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdatePersonalExpenseV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEToPersonalExpenseUpdateDto {

  PersonalExpenseUpdateDto from(UpdatePersonalExpenseV1Request req);

}
