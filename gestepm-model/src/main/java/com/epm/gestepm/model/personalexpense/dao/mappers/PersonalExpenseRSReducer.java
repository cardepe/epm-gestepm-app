package com.epm.gestepm.model.personalexpense.dao.mappers;

import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpense;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class PersonalExpenseRSReducer implements BinaryOperator<PersonalExpense> {

  @Override
  public PersonalExpense apply(PersonalExpense total, PersonalExpense current) {

    if (total == null) {
      return current;
    }

    if (!CollectionUtils.isEmpty(total.getFileIds()) && !CollectionUtils.isEmpty(current.getFileIds())) {

      final List<Integer> fileIds = new ArrayList<>();
      fileIds.addAll(total.getFileIds());
      fileIds.addAll(current.getFileIds());

      total.setFileIds(fileIds);
    }

    return total;
  }

}
