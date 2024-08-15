package com.epm.gestepm.model.constructionsharefile.dao;

import com.epm.gestepm.modelapi.constructionsharefile.dto.ConstructionShareFile;

public interface ConstructionShareFileRepositoryCustom {
	ConstructionShareFile findByConstructionShareId(Long constructionShareId);
}
