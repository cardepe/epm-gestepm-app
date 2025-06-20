package com.epm.gestepm.model.deprecated.constructionshare.dao;

import com.epm.gestepm.modelapi.deprecated.constructionshare.dto.ConstructionShare;

import java.time.LocalDateTime;
import java.util.List;

public interface ConstructionShareRepositoryCustom {

	List<ConstructionShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
}
