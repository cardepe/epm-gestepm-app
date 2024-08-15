package com.epm.gestepm.modelapi.interventionsharefile.service;

import com.epm.gestepm.modelapi.interventionsharefile.dto.InterventionShareFile;

public interface InterventionShareFileService {

	InterventionShareFile save(InterventionShareFile interventionShareFile);
	
	InterventionShareFile getInterventionShareFileById(Long interventionShareFileId);
	
	InterventionShareFile getInterventionShareFileByInterventionShareId(Long interventionShareId);

}
