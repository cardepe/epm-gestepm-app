package com.epm.gestepm.masterdata.country.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.country.dao.entity.Country;
import com.epm.gestepm.masterdata.country.dao.entity.creator.CountryCreate;
import com.epm.gestepm.masterdata.country.dao.entity.deleter.CountryDelete;
import com.epm.gestepm.masterdata.country.dao.entity.filter.CountryFilter;
import com.epm.gestepm.masterdata.country.dao.entity.finder.CountryByIdFinder;
import com.epm.gestepm.masterdata.country.dao.entity.updater.CountryUpdate;
import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface CountryDao {

  List<Country> list(@Valid CountryFilter filter);

  Page<Country> list(@Valid CountryFilter filter, Long offset, Long limit);

  Optional<@Valid Country> find(@Valid CountryByIdFinder finder);

  @Valid
  Country create(@Valid CountryCreate create);

  @Valid
  Country update(@Valid CountryUpdate update);

  void delete(@Valid CountryDelete delete);

}
