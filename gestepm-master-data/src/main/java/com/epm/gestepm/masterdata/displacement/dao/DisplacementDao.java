package com.epm.gestepm.masterdata.displacement.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.displacement.dao.entity.Displacement;
import com.epm.gestepm.masterdata.displacement.dao.entity.creator.DisplacementCreate;
import com.epm.gestepm.masterdata.displacement.dao.entity.deleter.DisplacementDelete;
import com.epm.gestepm.masterdata.displacement.dao.entity.filter.DisplacementFilter;
import com.epm.gestepm.masterdata.displacement.dao.entity.finder.DisplacementByIdFinder;
import com.epm.gestepm.masterdata.displacement.dao.entity.updater.DisplacementUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface DisplacementDao {

    List<Displacement> list(@Valid DisplacementFilter filter);

    Page<Displacement> list(@Valid DisplacementFilter filter, Long offset, Long limit);

    Optional<@Valid Displacement> find(@Valid DisplacementByIdFinder finder);

    @Valid
    Displacement create(@Valid DisplacementCreate create);

    @Valid
    Displacement update(@Valid DisplacementUpdate update);

    void delete(@Valid DisplacementDelete delete);

}
