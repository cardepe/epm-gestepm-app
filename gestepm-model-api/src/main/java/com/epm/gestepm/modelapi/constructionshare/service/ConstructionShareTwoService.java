package com.epm.gestepm.modelapi.constructionshare.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.constructionshare.dto.filter.ConstructionShareFilterDto;

import javax.validation.Valid;

public interface ConstructionShareTwoService {

    Page<@Valid ConstructionShareDto> list(@Valid ConstructionShareFilterDto filterDto, Long offset, Long limit);

}
