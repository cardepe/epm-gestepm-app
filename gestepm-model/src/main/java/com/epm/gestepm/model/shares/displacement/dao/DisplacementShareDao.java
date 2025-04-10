package com.epm.gestepm.model.shares.displacement.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.displacement.dao.entity.DisplacementShare;
import com.epm.gestepm.model.shares.displacement.dao.entity.creator.DisplacementShareCreate;
import com.epm.gestepm.model.shares.displacement.dao.entity.deleter.DisplacementShareDelete;
import com.epm.gestepm.model.shares.displacement.dao.entity.filter.DisplacementShareFilter;
import com.epm.gestepm.model.shares.displacement.dao.entity.finder.DisplacementShareByIdFinder;
import com.epm.gestepm.model.shares.displacement.dao.entity.updater.DisplacementShareUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface DisplacementShareDao {

    List<DisplacementShare> list(@Valid DisplacementShareFilter filter);

    Page<@Valid DisplacementShare> list(@Valid DisplacementShareFilter filter, Long offset, Long limit);

    Optional<@Valid DisplacementShare> find(@Valid DisplacementShareByIdFinder finder);

    @Valid
    DisplacementShare create(@Valid DisplacementShareCreate create);

    @Valid
    DisplacementShare update(@Valid DisplacementShareUpdate update);

    void delete(@Valid DisplacementShareDelete delete);
}
