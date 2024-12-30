package com.epm.gestepm.rest.personalexpensesheet.mappers;

import com.epm.gestepm.modelapi.personalexpensesheet.dto.filter.PersonalExpenseSheetFilterDto;
import com.epm.gestepm.rest.personalexpensesheet.request.PersonalExpenseSheetListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapPESToPersonalExpenseSheetFilterDto {

  PersonalExpenseSheetFilterDto from(PersonalExpenseSheetListRestRequest req);

}
