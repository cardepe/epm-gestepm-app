package com.epm.gestepm.model.deprecated.interventionshare.dao;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;

import java.time.LocalDateTime;
import java.util.List;

public interface InterventionShareRepositoryCustom {

	List<InterventionShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);

}
