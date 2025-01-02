package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseFileCreate;
import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseFileCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEFToPersonalExpenseFileCreate {

  PersonalExpenseFileCreate from(PersonalExpenseFileCreateDto createDto);

}
