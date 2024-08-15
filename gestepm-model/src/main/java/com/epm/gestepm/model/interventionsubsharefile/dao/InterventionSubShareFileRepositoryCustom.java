package com.epm.gestepm.model.interventionsubsharefile.dao;

import com.epm.gestepm.modelapi.interventionsubsharefile.dto.InterventionSubShareFile;

public interface InterventionSubShareFileRepositoryCustom {
	InterventionSubShareFile findByInterventionSubShareId(Long interventionSubShareId);
}
