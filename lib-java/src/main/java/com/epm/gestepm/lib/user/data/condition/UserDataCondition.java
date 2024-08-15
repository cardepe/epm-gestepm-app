package com.epm.gestepm.lib.user.data.condition;

import com.epm.gestepm.lib.user.data.UserData;

public interface UserDataCondition<T extends UserData<?>> {

    boolean test(final T data);

}
