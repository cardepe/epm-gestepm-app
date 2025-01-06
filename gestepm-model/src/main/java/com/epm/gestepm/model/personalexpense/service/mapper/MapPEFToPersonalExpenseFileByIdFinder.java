package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.finder.PersonalExpenseFileByIdFinder;
import com.epm.gestepm.modelapi.personalexpense.dto.finder.PersonalExpenseFileByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEFToPersonalExpenseFileByIdFinder {

  PersonalExpenseFileByIdFinder from(PersonalExpenseFileByIdFinderDto finderDto);

}
