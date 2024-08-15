package com.epm.gestepm.lib.user.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.epm.gestepm.lib.threadstore.AbstractThreadLocalStoreProvider;
import com.epm.gestepm.lib.user.ThreadLocalUserProvider;
import com.epm.gestepm.lib.user.data.UserData;
import com.epm.gestepm.lib.user.data.resolver.UserDataResolver;

public class DefaultUserProvider extends AbstractThreadLocalStoreProvider<Map<Class<? extends UserData<?>>, Object>>
        implements ThreadLocalUserProvider {

    private final Map<Class<? extends UserData<?>>, UserDataResolver<? extends UserData<?>>> resolvers;

    public DefaultUserProvider() {
        this.resolvers = new HashMap<>();
    }

    @Override
    public Map<Class<? extends UserData<?>>, Object> newStore() {
        return new HashMap<>();
    }

    @Override
    public <T extends UserData<?>> Optional<T> get(Class<T> clazz) {

        resolve(clazz);

        final Object storedData = getStore().get(clazz);
        final T value = clazz.cast(storedData);

        return Optional.ofNullable(value);
    }

    @Override
    public <T extends UserData<?>> void set(Class<T> clazz, T value) {
        getStore().put(clazz, value);
    }

    @Override
    public void override(Class<? extends UserData<?>> clazz, UserData<?> data) {
        getStore().put(clazz, data);
    }

    @Override
    public void clear() {
        getStore().clear();
    }

    public <T extends UserData<?>> void register(Class<T> clazz, UserDataResolver<T> resolver) {
        this.resolvers.put(clazz, resolver);
    }

    private <T extends UserData<?>> void resolve(Class<T> clazz) {

        final Object value = getStore().get(clazz);

        if (value == null) {

            final UserDataResolver<? extends UserData<?>> dataResolver = resolvers.get(clazz);
            final UserData<?> userData = dataResolver.resolve();

            getStore().put(clazz, userData);
        }
    }

}
