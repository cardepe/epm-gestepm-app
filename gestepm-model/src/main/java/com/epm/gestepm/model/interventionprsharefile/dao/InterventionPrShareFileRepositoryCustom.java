package com.epm.gestepm.model.interventionprsharefile.dao;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShareFile;

public interface InterventionPrShareFileRepositoryCustom {
	InterventionPrShareFile findByInterventionPrShareId(Long interventionPrShareId);
}
