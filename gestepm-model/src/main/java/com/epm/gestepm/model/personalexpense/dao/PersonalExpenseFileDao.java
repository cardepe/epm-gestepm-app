package com.epm.gestepm.model.personalexpense.dao;

import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpenseFile;
import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseFileCreate;
import com.epm.gestepm.model.personalexpense.dao.entity.filter.PersonalExpenseFileFilter;
import com.epm.gestepm.model.personalexpense.dao.entity.finder.PersonalExpenseFileByIdFinder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface PersonalExpenseFileDao {

  List<PersonalExpenseFile> list(@Valid PersonalExpenseFileFilter filter);

  Optional<@Valid PersonalExpenseFile> find(@Valid PersonalExpenseFileByIdFinder finder);

  @Valid
  PersonalExpenseFile create(@Valid PersonalExpenseFileCreate create);

}
