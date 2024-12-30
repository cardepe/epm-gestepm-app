package com.epm.gestepm.model.personalexpensesheet.dao.mappers;

import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheet;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class PersonalExpenseSheetRSReducer implements BinaryOperator<PersonalExpenseSheet> {

  @Override
  public PersonalExpenseSheet apply(PersonalExpenseSheet total, PersonalExpenseSheet current) {

    if (total == null) {
      return current;
    }

    if (!CollectionUtils.isEmpty(total.getPersonalExpenseIds()) && !CollectionUtils.isEmpty(current.getPersonalExpenseIds())) {

      final List<Integer> personalExpenseIds = new ArrayList<>();
      personalExpenseIds.addAll(total.getPersonalExpenseIds());
      personalExpenseIds.addAll(current.getPersonalExpenseIds());

      total.setPersonalExpenseIds(personalExpenseIds);
    }

    return total;
  }

}
