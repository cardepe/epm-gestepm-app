package com.epm.gestepm.rest.personalexpense.mappers;

import com.epm.gestepm.modelapi.personalexpense.dto.finder.PersonalExpenseByIdFinderDto;
import com.epm.gestepm.rest.personalexpense.request.PersonalExpenseFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEToPersonalExpenseByIdFinderDto {

  PersonalExpenseByIdFinderDto from(PersonalExpenseFindRestRequest req);

}
