package com.epm.gestepm.model.interventionsharefile.dao;

import com.epm.gestepm.modelapi.interventionsharefile.dto.InterventionShareFile;

public interface InterventionShareFileRepositoryCustom {
	InterventionShareFile findByInterventionShareId(Long interventionShareId);
}
