package com.epm.gestepm.model.signings.teleworking.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.signings.teleworking.dao.entity.TeleworkingSigning;
import com.epm.gestepm.model.signings.teleworking.dao.entity.creator.TeleworkingSigningCreate;
import com.epm.gestepm.model.signings.teleworking.dao.entity.deleter.TeleworkingSigningDelete;
import com.epm.gestepm.model.signings.teleworking.dao.entity.filter.TeleworkingSigningFilter;
import com.epm.gestepm.model.signings.teleworking.dao.entity.finder.TeleworkingSigningByIdFinder;
import com.epm.gestepm.model.signings.teleworking.dao.entity.updater.TeleworkingSigningUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface TeleworkingSigningDao {

  List<TeleworkingSigning> list(@Valid TeleworkingSigningFilter filter);

  Page<TeleworkingSigning> list(@Valid TeleworkingSigningFilter filter, Long offset, Long limit);

  Optional<@Valid TeleworkingSigning> find(@Valid TeleworkingSigningByIdFinder finder);

  @Valid
  TeleworkingSigning create(@Valid TeleworkingSigningCreate create);

  @Valid
  TeleworkingSigning update(@Valid TeleworkingSigningUpdate update);

  void delete(@Valid TeleworkingSigningDelete delete);

}
