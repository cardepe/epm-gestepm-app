package com.epm.gestepm.masterdata.activitycenter.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.ActivityCenter;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.creator.ActivityCenterCreate;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.deleter.ActivityCenterDelete;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.filter.ActivityCenterFilter;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.finder.ActivityCenterByIdFinder;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.updater.ActivityCenterUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ActivityCenterDao {

  List<ActivityCenter> list(@Valid ActivityCenterFilter filter);

  Page<ActivityCenter> list(@Valid ActivityCenterFilter filter, Long offset, Long limit);

  Optional<@Valid ActivityCenter> find(@Valid ActivityCenterByIdFinder finder);

  @Valid
  ActivityCenter create(@Valid ActivityCenterCreate create);

  @Valid
  ActivityCenter update(@Valid ActivityCenterUpdate update);

  void delete(@Valid ActivityCenterDelete delete);

}
