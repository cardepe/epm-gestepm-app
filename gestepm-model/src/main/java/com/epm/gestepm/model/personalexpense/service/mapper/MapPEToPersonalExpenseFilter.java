package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.filter.PersonalExpenseFilter;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEToPersonalExpenseFilter {

  PersonalExpenseFilter from(PersonalExpenseFilterDto filterDto);

}
