package com.epm.gestepm.model.inspection.dao;

import com.epm.gestepm.model.inspection.dao.entity.InspectionFile;
import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionFileCreate;
import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFileFilter;
import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionFileByIdFinder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface InspectionFileDao {

  List<InspectionFile> list(@Valid InspectionFileFilter filter);

  Optional<@Valid InspectionFile> find(@Valid InspectionFileByIdFinder finder);

  @Valid
  InspectionFile create(@Valid InspectionFileCreate create);

}