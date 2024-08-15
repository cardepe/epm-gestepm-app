package com.epm.gestepm.lib.applocale.apimodel.service;

import java.util.List;
import java.util.Optional;
import com.epm.gestepm.lib.applocale.apimodel.dto.AppLocaleDto;
import com.epm.gestepm.lib.applocale.apimodel.dto.filter.AppLocaleFilterDto;
import com.epm.gestepm.lib.applocale.apimodel.dto.finder.AppLocaleByIdFinderDto;
import com.epm.gestepm.lib.types.Page;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface AppLocaleService {

    List<@Valid AppLocaleDto> list();

    List<@Valid AppLocaleDto> list(@Valid AppLocaleFilterDto filterDto);

    Page<@Valid AppLocaleDto> list(@Valid AppLocaleFilterDto filterDto, Long offset, Long limit);

    Optional<@Valid AppLocaleDto> find(@Valid AppLocaleByIdFinderDto finderDto);

    Optional<@Valid AppLocaleDto> findDefault();

    @Valid
    AppLocaleDto findDefaultOrNotFound();

}
