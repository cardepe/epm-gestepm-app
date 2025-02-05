package com.epm.gestepm.lib.testutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.epm.gestepm.lib.user.UserProvider;
import com.epm.gestepm.lib.user.data.UserData;
import com.epm.gestepm.lib.user.data.UserEmail;
import com.epm.gestepm.lib.user.data.UserLogin;
import com.epm.gestepm.lib.user.data.UserPermits;

public class MockUserProvider implements UserProvider {

    private final Map<Class<? extends UserData<?>>, Object> data;

    public MockUserProvider() {

        data = new HashMap<>();

        data.put(UserLogin.class, new UserLogin(1));
        data.put(UserEmail.class, new UserEmail("mock@mocked.com"));
        data.put(UserPermits.class, new UserPermits(List.of()));
    }

    @Override
    public <T extends UserData<?>> Optional<T> get(Class<T> clazz) {

        final Object stored = data.get(clazz);
        final T value = clazz.cast(stored);

        return Optional.ofNullable(value);
    }

    @Override
    public <T extends UserData<?>> void set(Class<T> clazz, T value) {
        data.put(clazz, value);
    }

    @Override
    public void override(Class<? extends UserData<?>> clazz, UserData<?> data) {

    }

    @Override
    public void clear() {

    }

}
