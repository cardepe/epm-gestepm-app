package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseCreate;
import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEToPersonalExpenseCreate {

  PersonalExpenseCreate from(PersonalExpenseCreateDto createDto);

}
