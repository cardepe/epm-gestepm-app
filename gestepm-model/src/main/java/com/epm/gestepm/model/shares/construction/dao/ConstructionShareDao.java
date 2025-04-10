package com.epm.gestepm.model.shares.construction.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShare;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShareFile;
import com.epm.gestepm.model.shares.construction.dao.entity.filter.ConstructionShareFilter;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShare;
import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareCreate;
import com.epm.gestepm.model.shares.construction.dao.entity.deleter.ConstructionShareDelete;
import com.epm.gestepm.model.shares.construction.dao.entity.filter.ConstructionShareFilter;
import com.epm.gestepm.model.shares.construction.dao.entity.finder.ConstructionShareByIdFinder;
import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ConstructionShareDao {

    List<ConstructionShare> list(@Valid ConstructionShareFilter filter);

    Page<@Valid ConstructionShare> list(@Valid ConstructionShareFilter filter, Long offset, Long limit);

    Optional<@Valid ConstructionShare> find(@Valid ConstructionShareByIdFinder finder);

    @Valid
    ConstructionShare create(@Valid ConstructionShareCreate create);

    @Valid
    ConstructionShare update(@Valid ConstructionShareUpdate update);

    void delete(@Valid ConstructionShareDelete delete);
}
