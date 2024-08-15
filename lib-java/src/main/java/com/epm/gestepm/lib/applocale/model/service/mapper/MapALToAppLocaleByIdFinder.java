package com.epm.gestepm.lib.applocale.model.service.mapper;

import com.epm.gestepm.lib.applocale.apimodel.dto.finder.AppLocaleByIdFinderDto;
import com.epm.gestepm.lib.applocale.model.dao.entity.finder.AppLocaleByIdFinder;
import org.mapstruct.Mapper;

@Mapper
public interface MapALToAppLocaleByIdFinder {

    AppLocaleByIdFinder from(AppLocaleByIdFinderDto finderDto);

}
