package com.epm.gestepm.model.inspection.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.inspection.dao.entity.Inspection;
import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionCreate;
import com.epm.gestepm.model.inspection.dao.entity.deleter.InspectionDelete;
import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFilter;
import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionByIdFinder;
import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface InspectionDao {

  List<Inspection> list(@Valid InspectionFilter filter);

  Page<Inspection> list(@Valid InspectionFilter filter, Long offset, Long limit);

  Optional<@Valid Inspection> find(@Valid InspectionByIdFinder finder);

  @Valid
  Inspection create(@Valid InspectionCreate create);

  @Valid
  Inspection update(@Valid InspectionUpdate update);

  void delete(@Valid InspectionDelete delete);

}
