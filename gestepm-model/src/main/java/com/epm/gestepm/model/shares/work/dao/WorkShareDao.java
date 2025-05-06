package com.epm.gestepm.model.shares.work.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.work.dao.entity.WorkShare;
import com.epm.gestepm.model.shares.work.dao.entity.creator.WorkShareCreate;
import com.epm.gestepm.model.shares.work.dao.entity.deleter.WorkShareDelete;
import com.epm.gestepm.model.shares.work.dao.entity.filter.WorkShareFilter;
import com.epm.gestepm.model.shares.work.dao.entity.finder.WorkShareByIdFinder;
import com.epm.gestepm.model.shares.work.dao.entity.updater.WorkShareUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface WorkShareDao {

    List<WorkShare> list(@Valid WorkShareFilter filter);

    Page<@Valid WorkShare> list(@Valid WorkShareFilter filter, Long offset, Long limit);

    Optional<@Valid WorkShare> find(@Valid WorkShareByIdFinder finder);

    @Valid
    WorkShare create(@Valid WorkShareCreate create);

    @Valid
    WorkShare update(@Valid WorkShareUpdate update);

    void delete(@Valid WorkShareDelete delete);
}
