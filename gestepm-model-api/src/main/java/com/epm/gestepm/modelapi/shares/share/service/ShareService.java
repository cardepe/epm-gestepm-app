package com.epm.gestepm.modelapi.shares.share.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.share.dto.ShareDto;
import com.epm.gestepm.modelapi.shares.share.dto.filter.ShareFilterDto;

import javax.validation.Valid;
import java.util.List;

public interface ShareService {

    List<@Valid ShareDto> list(@Valid ShareFilterDto filterDto);

    Page<@Valid ShareDto> list(@Valid ShareFilterDto filterDto, Long offset, Long limit);

}
