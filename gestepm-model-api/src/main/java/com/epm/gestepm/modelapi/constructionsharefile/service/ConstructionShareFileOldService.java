package com.epm.gestepm.modelapi.constructionsharefile.service;

import java.util.List;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.constructionsharefile.dto.ConstructionShareFile;
import org.springframework.web.multipart.MultipartFile;

public interface ConstructionShareFileOldService {

	ConstructionShareFile save(ConstructionShareFile constructionShareFile);
	
	ConstructionShareFile getConstructionShareFileById(Long constructionShareFileId);
	
	ConstructionShareFile getConstructionShareFileByInterventionShareId(Long constructionShareId);
	
	void uploadFiles(ConstructionShare constructionShare, List<MultipartFile> files) throws Exception;

}
