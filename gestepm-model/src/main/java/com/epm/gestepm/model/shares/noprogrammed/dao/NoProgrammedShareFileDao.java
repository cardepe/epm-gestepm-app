package com.epm.gestepm.model.shares.noprogrammed.dao;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShareFile;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareFileCreate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter.NoProgrammedShareFileDelete;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFileFilter;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareFileByIdFinder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface NoProgrammedShareFileDao {

  List<NoProgrammedShareFile> list(@Valid NoProgrammedShareFileFilter filter);

  Optional<@Valid NoProgrammedShareFile> find(@Valid NoProgrammedShareFileByIdFinder finder);

  @Valid
  NoProgrammedShareFile create(@Valid NoProgrammedShareFileCreate create);

  void delete(@Valid NoProgrammedShareFileDelete delete);

}
