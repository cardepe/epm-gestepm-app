package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.finder.PersonalExpenseByIdFinder;
import com.epm.gestepm.modelapi.personalexpense.dto.finder.PersonalExpenseByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEToPersonalExpenseByIdFinder {

  PersonalExpenseByIdFinder from(PersonalExpenseByIdFinderDto finderDto);

}
