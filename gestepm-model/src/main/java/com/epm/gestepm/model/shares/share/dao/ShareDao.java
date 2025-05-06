package com.epm.gestepm.model.shares.share.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.share.dao.entity.Share;
import com.epm.gestepm.model.shares.share.dao.entity.filter.ShareFilter;

import javax.validation.Valid;
import java.util.List;

public interface ShareDao {

    List<Share> list(@Valid ShareFilter filter);

    Page<@Valid Share> list(@Valid ShareFilter filter, Long offset, Long limit);

}
