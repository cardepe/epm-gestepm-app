package com.epm.gestepm.modelapi.constructionshare.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFilterDto;

import javax.validation.Valid;

public interface ConstructionShareTwoService {

    Page<@Valid ConstructionShareDto> list(@Valid ConstructionShareFilterDto filterDto, Long offset, Long limit);

}
