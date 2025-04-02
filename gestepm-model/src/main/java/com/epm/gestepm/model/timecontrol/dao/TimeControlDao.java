package com.epm.gestepm.model.timecontrol.dao;

import com.epm.gestepm.model.timecontrol.dao.entity.TimeControl;
import com.epm.gestepm.model.timecontrol.dao.entity.filter.TimeControlFilter;

import javax.validation.Valid;
import java.util.List;

public interface TimeControlDao {

  List<TimeControl> list(@Valid TimeControlFilter filter);

}
