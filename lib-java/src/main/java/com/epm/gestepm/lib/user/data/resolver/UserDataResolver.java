package com.epm.gestepm.lib.user.data.resolver;

import com.epm.gestepm.lib.user.data.UserData;

public interface UserDataResolver<T extends UserData<?>> {

    T resolve();

}
