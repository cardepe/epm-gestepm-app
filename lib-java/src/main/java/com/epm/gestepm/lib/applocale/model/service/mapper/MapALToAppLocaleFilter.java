package com.epm.gestepm.lib.applocale.model.service.mapper;

import com.epm.gestepm.lib.applocale.apimodel.dto.filter.AppLocaleFilterDto;
import com.epm.gestepm.lib.applocale.model.dao.entity.filter.AppLocaleFilter;
import org.mapstruct.Mapper;

@Mapper
public interface MapALToAppLocaleFilter {

    AppLocaleFilter from(AppLocaleFilterDto filterDto);

}
