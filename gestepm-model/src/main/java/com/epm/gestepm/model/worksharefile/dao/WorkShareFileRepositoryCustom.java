package com.epm.gestepm.model.worksharefile.dao;

import java.util.List;

import com.epm.gestepm.modelapi.worksharefile.dto.WorkShareFile;

public interface WorkShareFileRepositoryCustom {
	List<WorkShareFile> findByWorkShareId(Long workShareId);
}
