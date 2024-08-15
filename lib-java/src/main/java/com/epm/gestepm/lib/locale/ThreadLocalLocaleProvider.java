package com.epm.gestepm.lib.locale;

import com.epm.gestepm.lib.locale.store.LocaleStore;
import com.epm.gestepm.lib.threadstore.ThreadStoreProvider;

public interface ThreadLocalLocaleProvider extends LocaleProvider, ThreadStoreProvider<LocaleStore> {

}
