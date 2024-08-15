package com.epm.gestepm.lib.executiontrace.provider;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import com.epm.gestepm.lib.executiontrace.ThreadLocalExecutionRequestProvider;
import com.epm.gestepm.lib.executiontrace.store.ExecutionRequestStore;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.threadstore.AbstractThreadLocalStoreProvider;
import com.epm.gestepm.lib.utils.MDCUtils;

@EnableExecutionLog(layerMarker = DELEGATOR)
public class ThreadLocalExecutionRequestProviderImpl extends AbstractThreadLocalStoreProvider<ExecutionRequestStore>
        implements ThreadLocalExecutionRequestProvider {

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "[CTX] Acquiring execution request local store",
            logOut = false)
    public void acquire() {
        super.acquire();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgOut = "[CTX] Released execution request local store",
            logIn = false)
    public void release() {
        super.release();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "[CTX] Creating new request store",
            logOut = false)
    public ExecutionRequestStore newStore() {
        return new ExecutionRequestStore();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            logIn = false,
            msgOut = "[CTX] Retrieved traceId")
    public String getTraceId() {
        return MDCUtils.getTraceId();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            logIn = false,
            msgOut = "[CTX] Retrieved request method")
    public String getRequestMethod() {
        return getStore().getRequestMethod();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            logIn = false,
            msgOut = "[CTX] Retrieved request path")
    public String getRequestPath() {
        return getStore().getRequestPath();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "[CTX] Setting traceId",
            logOut = false)
    public void saveTraceId(String traceId) {
        // N/A
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "[CTX] Setting request method",
            logOut = false)
    public void saveRequestMethod(String method) {
        getStore().setRequestMethod(method);
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "[CTX] Setting request path",
            logOut = false)
    public void saveRequestPath(String path) {
        getStore().setRequestPath(path);
    }

}
