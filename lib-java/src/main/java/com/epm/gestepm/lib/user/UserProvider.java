package com.epm.gestepm.lib.user;

import java.util.Optional;
import java.util.function.Supplier;
import com.epm.gestepm.lib.user.data.UserData;
import com.epm.gestepm.lib.user.data.condition.UserDataCondition;
import com.epm.gestepm.lib.user.data.condition.UserDataConditionNotPassedException;
import com.epm.gestepm.lib.user.data.exception.MissingUserDataException;

public interface UserProvider {

    <T extends UserData<?>> Optional<T> get(Class<T> clazz);

    <T extends UserData<?>> void set(Class<T> clazz, T value);

    void override(Class<? extends UserData<?>> clazz, UserData<?> data);

    void clear();

    default <T extends UserData<?>> T getOrFail(Class<T> clazz) {
        return get(clazz).orElseThrow(MissingUserDataException::new);
    }

    default <T extends UserData<?>> boolean check(Class<T> clazz, UserDataCondition<T> condition) {
        final T value = get(clazz).orElse(null);
        return condition.test(value);
    }

    default <T extends UserData<?>> void require(Class<T> clazz, UserDataCondition<T> condition) {

        final boolean passed = check(clazz, condition);

        if (!passed) {
            throw new UserDataConditionNotPassedException();
        }
    }

    default <T extends UserData<?>> void requireOrFailWith(Class<T> clazz, UserDataCondition<T> condition,
            Supplier<RuntimeException> ex) {

        final boolean passed = check(clazz, condition);

        if (!passed) {
            throw ex.get();
        }
    }

}
