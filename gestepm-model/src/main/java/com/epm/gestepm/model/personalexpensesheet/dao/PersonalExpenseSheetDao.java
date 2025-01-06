package com.epm.gestepm.model.personalexpensesheet.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheet;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.creator.PersonalExpenseSheetCreate;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.deleter.PersonalExpenseSheetDelete;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.filter.PersonalExpenseSheetFilter;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.finder.PersonalExpenseSheetByIdFinder;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.updater.PersonalExpenseSheetUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface PersonalExpenseSheetDao {

  List<PersonalExpenseSheet> list(@Valid PersonalExpenseSheetFilter filter);

  Page<PersonalExpenseSheet> list(@Valid PersonalExpenseSheetFilter filter, Long offset, Long limit);

  Optional<@Valid PersonalExpenseSheet> find(@Valid PersonalExpenseSheetByIdFinder finder);

  @Valid
  PersonalExpenseSheet create(@Valid PersonalExpenseSheetCreate create);

  @Valid
  PersonalExpenseSheet update(@Valid PersonalExpenseSheetUpdate update);

  void delete(@Valid PersonalExpenseSheetDelete delete);

}
