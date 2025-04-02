package com.epm.gestepm.modelapi.timecontrol.service;

import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDto;
import com.epm.gestepm.modelapi.timecontrol.dto.filter.TimeControlFilterDto;

import javax.validation.Valid;
import java.util.List;

public interface TimeControlService {

    List<@Valid TimeControlDto> list(@Valid TimeControlFilterDto filterDto);

}
