package com.epm.gestepm.rest.personalexpensesheet.mappers;

import com.epm.gestepm.modelapi.personalexpensesheet.dto.finder.PersonalExpenseSheetByIdFinderDto;
import com.epm.gestepm.rest.personalexpensesheet.request.PersonalExpenseSheetFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapPESToPersonalExpenseSheetByIdFinderDto {

  PersonalExpenseSheetByIdFinderDto from(PersonalExpenseSheetFindRestRequest req);

}
