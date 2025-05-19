package com.epm.gestepm.model.shares.breaks.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.breaks.dao.entity.ShareBreak;
import com.epm.gestepm.model.shares.breaks.dao.entity.creator.ShareBreakCreate;
import com.epm.gestepm.model.shares.breaks.dao.entity.deleter.ShareBreakDelete;
import com.epm.gestepm.model.shares.breaks.dao.entity.filter.ShareBreakFilter;
import com.epm.gestepm.model.shares.breaks.dao.entity.finder.ShareBreakByIdFinder;
import com.epm.gestepm.model.shares.breaks.dao.entity.updater.ShareBreakUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ShareBreakDao {

    List<ShareBreak> list(@Valid ShareBreakFilter filter);

    Page<@Valid ShareBreak> list(@Valid ShareBreakFilter filter, Long offset, Long limit);

    Optional<@Valid ShareBreak> find(@Valid ShareBreakByIdFinder finder);

    @Valid
    ShareBreak create(@Valid ShareBreakCreate create);

    @Valid
    ShareBreak update(@Valid ShareBreakUpdate update);

    void delete(@Valid ShareBreakDelete delete);
}
