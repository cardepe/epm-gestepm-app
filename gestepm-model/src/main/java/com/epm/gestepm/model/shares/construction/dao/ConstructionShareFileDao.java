package com.epm.gestepm.model.shares.construction.dao;

import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShareFile;
import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareFileCreate;
import com.epm.gestepm.model.shares.construction.dao.entity.deleter.ConstructionShareFileDelete;
import com.epm.gestepm.model.shares.construction.dao.entity.filter.ConstructionShareFileFilter;
import com.epm.gestepm.model.shares.construction.dao.entity.finder.ConstructionShareFileByIdFinder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ConstructionShareFileDao {

  List<ConstructionShareFile> list(@Valid ConstructionShareFileFilter filter);

  Optional<@Valid ConstructionShareFile> find(@Valid ConstructionShareFileByIdFinder finder);

  @Valid
  ConstructionShareFile create(@Valid ConstructionShareFileCreate create);

  void delete(@Valid ConstructionShareFileDelete delete);

}
