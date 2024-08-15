package com.epm.gestepm.lib.security;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import com.epm.gestepm.lib.security.annotation.RequireOneOfPermits;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.security.exception.NotEnoughPermissionException;
import com.epm.gestepm.lib.user.UserProvider;
import com.epm.gestepm.lib.user.data.UserPermits;
import com.epm.gestepm.lib.user.data.condition.UserHasAllOfPermits;
import com.epm.gestepm.lib.user.data.condition.UserHasOneOfPermits;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(10)
@Component
public class PermissionCheckAspect {

    private final UserProvider userProvider;

    public PermissionCheckAspect(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Before(value = "@annotation(execution)", argNames = "execution")
    public void checkPermits(final RequirePermits execution) {

        final String action = execution.action();
        final List<String> required = Arrays.asList(execution.value());
        final Supplier<RuntimeException> notEnoughPermission = () -> new NotEnoughPermissionException(action, required);

        final UserHasAllOfPermits permitCondition = new UserHasAllOfPermits(required);
        // FIXME: enable again - userProvider.requireOrFailWith(UserPermits.class, permitCondition, notEnoughPermission);
    }

    @Before(value = "@annotation(execution)", argNames = "execution")
    public void checkPermits(final RequireOneOfPermits execution) {

        final String action = execution.action();
        final List<String> required = Arrays.asList(execution.value());
        final Supplier<RuntimeException> notEnoughPermission = () -> new NotEnoughPermissionException(action, required);

        final UserHasOneOfPermits permitCondition = new UserHasOneOfPermits(required);
        // FIXME: enable again - userProvider.requireOrFailWith(UserPermits.class, permitCondition, notEnoughPermission);
    }

}
