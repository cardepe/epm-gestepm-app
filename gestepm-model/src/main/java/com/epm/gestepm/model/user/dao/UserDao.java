package com.epm.gestepm.model.user.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.user.dao.entity.User;
import com.epm.gestepm.model.user.dao.entity.creator.UserCreate;
import com.epm.gestepm.model.user.dao.entity.deleter.UserDelete;
import com.epm.gestepm.model.user.dao.entity.filter.UserFilter;
import com.epm.gestepm.model.user.dao.entity.finder.UserByIdFinder;
import com.epm.gestepm.model.user.dao.entity.updater.UserUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> list(@Valid UserFilter filter);

    Page<@Valid User> list(@Valid UserFilter filter, Long offset, Long limit);

    Optional<@Valid User> find(@Valid UserByIdFinder finder);

    @Valid
    User create(@Valid UserCreate create);

    @Valid
    User update(@Valid UserUpdate update);

    void delete(@Valid UserDelete delete);
}
