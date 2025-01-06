package com.epm.gestepm.model.personalexpense.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpense;
import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseCreate;
import com.epm.gestepm.model.personalexpense.dao.entity.deleter.PersonalExpenseDelete;
import com.epm.gestepm.model.personalexpense.dao.entity.filter.PersonalExpenseFilter;
import com.epm.gestepm.model.personalexpense.dao.entity.finder.PersonalExpenseByIdFinder;
import com.epm.gestepm.model.personalexpense.dao.entity.updater.PersonalExpenseUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface PersonalExpenseDao {

  List<PersonalExpense> list(@Valid PersonalExpenseFilter filter);

  Page<PersonalExpense> list(@Valid PersonalExpenseFilter filter, Long offset, Long limit);

  Optional<@Valid PersonalExpense> find(@Valid PersonalExpenseByIdFinder finder);

  @Valid
  PersonalExpense create(@Valid PersonalExpenseCreate create);

  @Valid
  PersonalExpense update(@Valid PersonalExpenseUpdate update);

  void delete(@Valid PersonalExpenseDelete delete);

}
