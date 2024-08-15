package com.epm.gestepm.lib.applocale.model.service.mapper;

import java.util.List;
import com.epm.gestepm.lib.applocale.apimodel.dto.AppLocaleDto;
import com.epm.gestepm.lib.applocale.model.dao.entity.AppLocale;
import com.epm.gestepm.lib.types.Page;
import org.mapstruct.Mapper;

@Mapper
public interface MapALToAppLocaleDto {

    AppLocaleDto from(AppLocale appLocale);

    List<AppLocaleDto> from(List<AppLocale> appLocale);

    default Page<AppLocaleDto> from(Page<AppLocale> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
