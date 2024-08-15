package com.epm.gestepm.lib.executiontrace.provider;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import com.epm.gestepm.lib.executiontrace.ThreadLocalExecutionTimeProvider;
import com.epm.gestepm.lib.executiontrace.store.ExecutionTimeStore;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.threadstore.AbstractThreadLocalStoreProvider;

@EnableExecutionLog(layerMarker = DELEGATOR)
public class DefaultExecutionTimeProvider extends AbstractThreadLocalStoreProvider<ExecutionTimeStore> implements
        ThreadLocalExecutionTimeProvider {

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "CTX - Acquiring execution time local store",
            logOut = false)
    public void acquire() {
        super.acquire();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgOut = "CTX - Released execution time local store",
            logIn = false)
    public void release() {
        super.release();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "CTX - Creating new time store",
            logOut = false)
    public ExecutionTimeStore newStore() {
        return new ExecutionTimeStore();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "CTX - Starting time monitoring",
            logOut = false)
    public void start() {
        this.getStore().setStartExecutionTime(System.currentTimeMillis());
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgOut = "CTX - Obtaining request time start",
            logIn = false)
    public Long getStartExecutionTime() {
        return this.getStore().getStartExecutionTime();
    }

}
