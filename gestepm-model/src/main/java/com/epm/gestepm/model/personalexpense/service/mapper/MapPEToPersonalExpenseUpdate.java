package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.updater.PersonalExpenseUpdate;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseDto;
import com.epm.gestepm.modelapi.personalexpense.dto.updater.PersonalExpenseUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapPEToPersonalExpenseUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  PersonalExpenseUpdate from(PersonalExpenseUpdateDto source, @MappingTarget PersonalExpenseUpdate target);

  PersonalExpenseUpdate from(PersonalExpenseUpdateDto updateDto);

  PersonalExpenseUpdate from(PersonalExpenseDto dto);

}
