package com.epm.gestepm.model.activitycenter.dao;

import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import org.springframework.data.repository.CrudRepository;

public interface ActivityCenterRepository extends CrudRepository<ActivityCenter, Long>, ActivityCenterRepositoryCustom {
	
}
