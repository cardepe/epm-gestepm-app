package com.epm.gestepm.lib.user.aspect;

import com.epm.gestepm.lib.user.UserProvider;
import com.epm.gestepm.lib.user.annotation.ClearUserProvider;
import com.epm.gestepm.lib.user.annotation.UserProviderOverride;
import com.epm.gestepm.lib.user.annotation.UserProviderOverrides;
import com.epm.gestepm.lib.user.data.UserData;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(0)
@Component
public class UserProviderAspect {

    private final ApplicationContext applicationContext;

    private final UserProvider userProvider;

    public UserProviderAspect(ApplicationContext applicationContext, UserProvider userProvider) {
        this.applicationContext = applicationContext;
        this.userProvider = userProvider;
    }

    @Around(value = "@annotation(annotations)")
    public Object overrideUserProvider(final ProceedingJoinPoint joinPoint, UserProviderOverrides annotations)
            throws Throwable {

        for (UserProviderOverride ann : annotations.value()) {

            final Class<? extends UserData<?>> clazz = ann.userDataClass();
            final String valueBean = ann.value();

            final UserData<?> userData = applicationContext.getBean(valueBean, clazz);

            userProvider.override(clazz, userData);
        }

        final Object methodReturnValue = joinPoint.proceed();

        userProvider.clear();

        return methodReturnValue;
    }

    @Before("@annotation(ann)")
    public void clearUserProvider(final JoinPoint joinPoint, ClearUserProvider ann) {

        userProvider.clear();
    }

}
