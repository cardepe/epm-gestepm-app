package com.epm.gestepm.lib.executiontrace;

import com.epm.gestepm.lib.executiontrace.store.ExecutionTimeStore;
import com.epm.gestepm.lib.threadstore.ThreadStoreProvider;

public interface ThreadLocalExecutionTimeProvider extends ExecutionTimeProvider,
        ThreadStoreProvider<ExecutionTimeStore> {

}
