package com.epm.gestepm.model.constructionshare.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.constructionshare.dao.entity.ConstructionShare;
import com.epm.gestepm.model.constructionshare.dao.entity.filter.ConstructionShareFilter;

import javax.validation.Valid;

public interface ConstructionShareDao {

    Page<@Valid ConstructionShare> list(@Valid ConstructionShareFilter filter, Long offset, Long limit);

}
