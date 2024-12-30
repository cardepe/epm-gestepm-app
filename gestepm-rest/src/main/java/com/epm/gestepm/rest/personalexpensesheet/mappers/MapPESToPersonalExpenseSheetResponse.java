package com.epm.gestepm.rest.personalexpensesheet.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;
import com.epm.gestepm.restapi.openapi.model.PersonalExpense;
import com.epm.gestepm.restapi.openapi.model.PersonalExpenseSheet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface MapPESToPersonalExpenseSheetResponse {

  @Mapping(source = "userId", target = "user.id")
  @Mapping(source = "projectId", target = "project.id")
  @Mapping(source = "personalExpenseIds", target = "personalExpenses", qualifiedByName = "mapPersonalExpenses")
  PersonalExpenseSheet from(PersonalExpenseSheetDto dto);

  List<PersonalExpenseSheet> from(Page<PersonalExpenseSheetDto> list);

  @Named("mapPersonalExpenses")
  static List<PersonalExpense> mapPersonalExpenses(final List<Integer> personalExpenseIds) {
    return personalExpenseIds.stream().map(id -> {
      final PersonalExpense personalExpense = new PersonalExpense();
      personalExpense.setId(id);
      return personalExpense;

    }).collect(Collectors.toList());
  }
}
