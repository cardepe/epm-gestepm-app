package com.epm.gestepm.model.shares.programmed.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShare;
import com.epm.gestepm.model.shares.programmed.dao.entity.creator.ProgrammedShareCreate;
import com.epm.gestepm.model.shares.programmed.dao.entity.deleter.ProgrammedShareDelete;
import com.epm.gestepm.model.shares.programmed.dao.entity.filter.ProgrammedShareFilter;
import com.epm.gestepm.model.shares.programmed.dao.entity.finder.ProgrammedShareByIdFinder;
import com.epm.gestepm.model.shares.programmed.dao.entity.updater.ProgrammedShareUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProgrammedShareDao {

    List<ProgrammedShare> list(@Valid ProgrammedShareFilter filter);

    Page<@Valid ProgrammedShare> list(@Valid ProgrammedShareFilter filter, Long offset, Long limit);

    Optional<@Valid ProgrammedShare> find(@Valid ProgrammedShareByIdFinder finder);

    @Valid
    ProgrammedShare create(@Valid ProgrammedShareCreate create);

    @Valid
    ProgrammedShare update(@Valid ProgrammedShareUpdate update);

    void delete(@Valid ProgrammedShareDelete delete);
}
