package com.epm.gestepm.lib.applocale.model.service;

import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import static org.mapstruct.factory.Mappers.getMapper;
import java.util.List;
import java.util.Optional;
import com.epm.gestepm.lib.applocale.apimodel.dto.AppLocaleDto;
import com.epm.gestepm.lib.applocale.apimodel.dto.filter.AppLocaleFilterDto;
import com.epm.gestepm.lib.applocale.apimodel.dto.finder.AppLocaleByIdFinderDto;
import com.epm.gestepm.lib.applocale.apimodel.exception.DefaultAppLocaleNotFoundException;
import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.applocale.model.dao.AppLocaleDao;
import com.epm.gestepm.lib.applocale.model.dao.entity.AppLocale;
import com.epm.gestepm.lib.applocale.model.dao.entity.filter.AppLocaleFilter;
import com.epm.gestepm.lib.applocale.model.dao.entity.finder.AppLocaleByIdFinder;
import com.epm.gestepm.lib.applocale.model.service.mapper.MapALToAppLocaleByIdFinder;
import com.epm.gestepm.lib.applocale.model.service.mapper.MapALToAppLocaleDto;
import com.epm.gestepm.lib.applocale.model.service.mapper.MapALToAppLocaleFilter;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.logging.constants.LogLayerMarkers;
import com.epm.gestepm.lib.types.Page;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;

@Validated
@EnableExecutionLog(layerMarker = LogLayerMarkers.SERVICE)
public class AppLocaleServiceImpl implements AppLocaleService {

    private final AppLocaleDao appLocaleDao;

    public AppLocaleServiceImpl(AppLocaleDao appLocaleDao) {
        this.appLocaleDao = appLocaleDao;
    }

    @Override
    @Cacheable(sync = true, value = "${lib-java.cache-keys.app-locale-list}", keyGenerator = "defaultCacheKeyGenerator", cacheResolver = "defaultCacheResolver")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing app locales",
            msgOut = "Listing app locales OK",
            errorMsg = "Failed to list app locales")
    public List<AppLocaleDto> list() {
        return this.list(new AppLocaleFilterDto());
    }

    @Override
    @Cacheable(sync = true, value = "${lib-java.cache-keys.app-locale-list}", keyGenerator = "defaultCacheKeyGenerator", cacheResolver = "defaultCacheResolver")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing app locales",
            msgOut = "Listing app locales OK",
            errorMsg = "Failed to list app locales")
    public List<AppLocaleDto> list(AppLocaleFilterDto filterDto) {

        final AppLocaleFilter filter = getMapper(MapALToAppLocaleFilter.class).from(filterDto);
        final List<AppLocale> list = this.appLocaleDao.list(filter);

        return getMapper(MapALToAppLocaleDto.class).from(list);
    }

    @Override
    @Cacheable(sync = true, value = "${lib-java.cache-keys.app-locale-list}", keyGenerator = "defaultCacheKeyGenerator", cacheResolver = "defaultCacheResolver")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating app locales",
            msgOut = "Paginating app locales OK",
            errorMsg = "Failed to paginate app locales")
    public Page<AppLocaleDto> list(AppLocaleFilterDto filterDto, Long offset,
                                   Long limit) {

        final AppLocaleFilter filter = getMapper(MapALToAppLocaleFilter.class).from(filterDto);
        final Page<AppLocale> page = this.appLocaleDao.list(filter, offset, limit);

        return getMapper(MapALToAppLocaleDto.class).from(page);
    }

    @Override
    @Cacheable(sync = true, value = "${lib-java.cache-keys.app-locale}", keyGenerator = "defaultCacheKeyGenerator", cacheResolver = "defaultCacheResolver")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding app locale by ID, result can be empty",
            msgOut = "Found app locale by ID",
            errorMsg = "Failed to find app locale by ID")
    public Optional<AppLocaleDto> find(AppLocaleByIdFinderDto finderDto) {

        final AppLocaleByIdFinder finder = getMapper(MapALToAppLocaleByIdFinder.class).from(finderDto);
        final Optional<AppLocale> found = this.appLocaleDao.find(finder);

        return found.map(getMapper(MapALToAppLocaleDto.class)::from);
    }

    @Override
    @Cacheable(sync = true, value = "${lib-java.cache-keys.app-locale}", keyGenerator = "defaultCacheKeyGenerator", cacheResolver = "defaultCacheResolver")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding default app locale, result can be empty",
            msgOut = "Found default app locale",
            errorMsg = "Failed to find default app locale")
    public Optional<AppLocaleDto> findDefault() {

        final AppLocaleFilter localeFilter = new AppLocaleFilter();
        localeFilter.setDefault(true);

        final Page<AppLocale> page = this.appLocaleDao.list(localeFilter, 0L, 1L);

        return page.get(0).map(getMapper(MapALToAppLocaleDto.class)::from);
    }

    @Override
    @Cacheable(sync = true, value = "${lib-java.cache-keys.app-locale}", keyGenerator = "defaultCacheKeyGenerator", cacheResolver = "defaultCacheResolver")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding default app locale, result is expected or will fail",
            msgOut = "Found default app locale",
            errorMsg = "Default app locale not found")
    public AppLocaleDto findDefaultOrNotFound() {
        return this.findDefault().orElseThrow(DefaultAppLocaleNotFoundException::new);
    }

}
