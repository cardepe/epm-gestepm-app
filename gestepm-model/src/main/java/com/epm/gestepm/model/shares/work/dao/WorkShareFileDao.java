package com.epm.gestepm.model.shares.work.dao;

import com.epm.gestepm.model.shares.work.dao.entity.WorkShareFile;
import com.epm.gestepm.model.shares.work.dao.entity.creator.WorkShareFileCreate;
import com.epm.gestepm.model.shares.work.dao.entity.deleter.WorkShareFileDelete;
import com.epm.gestepm.model.shares.work.dao.entity.filter.WorkShareFileFilter;
import com.epm.gestepm.model.shares.work.dao.entity.finder.WorkShareFileByIdFinder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface WorkShareFileDao {

  List<WorkShareFile> list(@Valid WorkShareFileFilter filter);

  Optional<@Valid WorkShareFile> find(@Valid WorkShareFileByIdFinder finder);

  @Valid
  WorkShareFile create(@Valid WorkShareFileCreate create);

  void delete(@Valid WorkShareFileDelete delete);

}
