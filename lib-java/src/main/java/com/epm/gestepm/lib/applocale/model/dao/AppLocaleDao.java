package com.epm.gestepm.lib.applocale.model.dao;

import java.util.List;
import java.util.Optional;
import com.epm.gestepm.lib.applocale.model.dao.entity.AppLocale;
import com.epm.gestepm.lib.applocale.model.dao.entity.filter.AppLocaleFilter;
import com.epm.gestepm.lib.applocale.model.dao.entity.finder.AppLocaleByIdFinder;
import com.epm.gestepm.lib.types.Page;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface AppLocaleDao {

    List<AppLocale> list(@Valid final AppLocaleFilter filter);

    Page<AppLocale> list(@Valid final AppLocaleFilter filter, @NotNull @Min(0) Long offset,
            @NotNull @Min(1) Long limit);

    Optional<@Valid AppLocale> find(@Valid final AppLocaleByIdFinder finder);

}
