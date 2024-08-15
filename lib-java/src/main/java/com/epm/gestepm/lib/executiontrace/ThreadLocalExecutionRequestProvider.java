package com.epm.gestepm.lib.executiontrace;

import com.epm.gestepm.lib.executiontrace.store.ExecutionRequestStore;
import com.epm.gestepm.lib.threadstore.ThreadStoreProvider;

public interface ThreadLocalExecutionRequestProvider extends ExecutionRequestProvider,
        ThreadStoreProvider<ExecutionRequestStore> {

}
