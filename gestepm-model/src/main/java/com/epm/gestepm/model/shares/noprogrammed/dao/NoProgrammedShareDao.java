package com.epm.gestepm.model.shares.noprogrammed.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareCreate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter.NoProgrammedShareDelete;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFilter;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface NoProgrammedShareDao {

  List<NoProgrammedShare> list(@Valid NoProgrammedShareFilter filter);

  Page<NoProgrammedShare> list(@Valid NoProgrammedShareFilter filter, Long offset, Long limit);

  Optional<@Valid NoProgrammedShare> find(@Valid NoProgrammedShareByIdFinder finder);

  @Valid
  NoProgrammedShare create(@Valid NoProgrammedShareCreate create);

  @Valid
  NoProgrammedShare update(@Valid NoProgrammedShareUpdate update);

  void delete(@Valid NoProgrammedShareDelete delete);

}
