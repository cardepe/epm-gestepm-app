package com.epm.gestepm.modelapi.worksharefile.service;

import com.epm.gestepm.modelapi.worksharefile.dto.WorkShareFile;

import java.util.List;

public interface WorkShareFileService {

	WorkShareFile save(WorkShareFile workShareFile);
	
	WorkShareFile getWorkShareFileById(Long workShareFileId);
	
	List<WorkShareFile> getWorkShareFileByWorkShareId(Long workShareId);

}
