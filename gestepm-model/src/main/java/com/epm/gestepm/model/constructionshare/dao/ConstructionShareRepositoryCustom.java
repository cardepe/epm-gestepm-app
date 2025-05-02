package com.epm.gestepm.model.constructionshare.dao;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ConstructionShareRepositoryCustom {

	List<ConstructionShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
}
