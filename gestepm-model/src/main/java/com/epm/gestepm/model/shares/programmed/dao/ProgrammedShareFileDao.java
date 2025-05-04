package com.epm.gestepm.model.shares.programmed.dao;

import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShareFile;
import com.epm.gestepm.model.shares.programmed.dao.entity.creator.ProgrammedShareFileCreate;
import com.epm.gestepm.model.shares.programmed.dao.entity.deleter.ProgrammedShareFileDelete;
import com.epm.gestepm.model.shares.programmed.dao.entity.filter.ProgrammedShareFileFilter;
import com.epm.gestepm.model.shares.programmed.dao.entity.finder.ProgrammedShareFileByIdFinder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProgrammedShareFileDao {

  List<ProgrammedShareFile> list(@Valid ProgrammedShareFileFilter filter);

  Optional<@Valid ProgrammedShareFile> find(@Valid ProgrammedShareFileByIdFinder finder);

  @Valid
  ProgrammedShareFile create(@Valid ProgrammedShareFileCreate create);

  void delete(@Valid ProgrammedShareFileDelete delete);

}
