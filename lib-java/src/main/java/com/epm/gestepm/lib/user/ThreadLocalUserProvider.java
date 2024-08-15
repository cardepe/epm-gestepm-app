package com.epm.gestepm.lib.user;

import java.util.Map;
import com.epm.gestepm.lib.threadstore.ThreadStoreProvider;
import com.epm.gestepm.lib.user.data.UserData;

public interface ThreadLocalUserProvider
        extends UserProvider, ThreadStoreProvider<Map<Class<? extends UserData<?>>, Object>> {

}
