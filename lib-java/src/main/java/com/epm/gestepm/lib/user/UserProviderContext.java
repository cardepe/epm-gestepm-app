package com.epm.gestepm.lib.user;

import java.util.Optional;
import com.epm.gestepm.lib.user.data.UserData;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class UserProviderContext implements ApplicationContextAware {

    private static ApplicationContext context;

    public static Optional<UserProvider> get() {

        UserProvider userProvider;

        try {
            userProvider = context.getBean(UserProvider.class);
        } catch (Exception ex) {
            userProvider = null;
        }

        return Optional.ofNullable(userProvider);
    }

    public static <T extends UserData<?>> Optional<T> get(Class<T> clazz) {

        Optional<T> value = Optional.empty();

        final Optional<UserProvider> userProvider = get();

        if (userProvider.isPresent()) {
            value = userProvider.get().get(clazz);
        }

        return value;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserProviderContext.context = applicationContext;
    }

}
