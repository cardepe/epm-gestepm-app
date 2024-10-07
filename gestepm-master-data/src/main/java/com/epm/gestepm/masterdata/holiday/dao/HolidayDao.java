package com.epm.gestepm.masterdata.holiday.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.holiday.dao.entity.Holiday;
import com.epm.gestepm.masterdata.holiday.dao.entity.creator.HolidayCreate;
import com.epm.gestepm.masterdata.holiday.dao.entity.deleter.HolidayDelete;
import com.epm.gestepm.masterdata.holiday.dao.entity.filter.HolidayFilter;
import com.epm.gestepm.masterdata.holiday.dao.entity.finder.HolidayByIdFinder;
import com.epm.gestepm.masterdata.holiday.dao.entity.updater.HolidayUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface HolidayDao {

    List<Holiday> list(@Valid HolidayFilter filter);

    Page<Holiday> list(@Valid HolidayFilter filter, Long offset, Long limit);

    Optional<@Valid Holiday> find(@Valid HolidayByIdFinder finder);

    @Valid
    Holiday create(@Valid HolidayCreate create);

    @Valid
    Holiday update(@Valid HolidayUpdate update);

    void delete(@Valid HolidayDelete delete);

}
