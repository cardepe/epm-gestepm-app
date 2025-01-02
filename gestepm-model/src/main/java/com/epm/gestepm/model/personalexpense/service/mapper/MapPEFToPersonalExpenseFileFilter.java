package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.filter.PersonalExpenseFileFilter;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFileFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEFToPersonalExpenseFileFilter {

  PersonalExpenseFileFilter from(PersonalExpenseFileFilterDto filterDto);

}
