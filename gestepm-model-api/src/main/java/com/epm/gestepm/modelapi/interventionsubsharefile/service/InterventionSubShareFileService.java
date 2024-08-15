package com.epm.gestepm.modelapi.interventionsubsharefile.service;

import java.util.List;

import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.interventionsubsharefile.dto.InterventionSubShareFile;
import org.springframework.web.multipart.MultipartFile;

public interface InterventionSubShareFileService {

	InterventionSubShareFile save(InterventionSubShareFile interventionSubShareFile);
	
	InterventionSubShareFile getInterventionSubShareFileById(Long interventionSubShareFileId);
	
	InterventionSubShareFile getInterventionSubShareFileByInterventionShareId(Long interventionSubShareId);
	
	void uploadFiles(InterventionSubShare interventionSubShare, List<MultipartFile> files) throws Exception;

}
