package com.epm.gestepm.lib.locale.provider;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import java.util.Optional;
import com.epm.gestepm.lib.locale.ThreadLocalLocaleProvider;
import com.epm.gestepm.lib.locale.store.LocaleStore;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.threadstore.AbstractThreadLocalStoreProvider;

@EnableExecutionLog(layerMarker = DELEGATOR)
public class DefaultLocaleProvider extends AbstractThreadLocalStoreProvider<LocaleStore> implements
        ThreadLocalLocaleProvider {

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "CTX - Acquiring locale store",
            logOut = false)
    public void acquire() {
        super.acquire();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgOut = "CTX - Released locale store",
            logIn = false)
    public void release() {
        super.release();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgOut = "CTX - Retrieved locale",
            logIn = false)
    public Optional<String> getLocale() {
        return getStore().getLocale();
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "CTX - Setting locale",
            logOut = false)
    public void setLocale(String locale) {
        getStore().setLocale(locale);
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            level = "TRACE",
            msgIn = "CTX - Created new locale store",
            logOut = false)
    public LocaleStore newStore() {
        return new LocaleStore();
    }

}
