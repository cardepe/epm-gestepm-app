package com.epm.gestepm.modelapi.interventionprshare.service;

import java.util.List;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShareFile;
import org.springframework.web.multipart.MultipartFile;

public interface InterventionPrShareFileService {

	InterventionPrShareFile save(InterventionPrShareFile interventionPrShareFile);
	
	InterventionPrShareFile getInterventionPrShareFileById(Long interventionPrShareFileId);
	
	InterventionPrShareFile getInterventionPrShareFileByInterventionShareId(Long interventionPrShareId);
	
	void uploadFiles(InterventionPrShare interventionPrShare, List<MultipartFile> files) throws Exception;

}
