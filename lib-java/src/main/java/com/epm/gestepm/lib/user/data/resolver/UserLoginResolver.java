package com.epm.gestepm.lib.user.data.resolver;

import com.epm.gestepm.lib.user.data.UserLogin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserLoginResolver implements UserDataResolver<UserLogin> {

    @Override
    public UserLogin resolve() {

        UserLogin userLogin = null;

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            final Object principal = authentication.getPrincipal();
            final boolean authenticated = authentication.isAuthenticated();

            if (authenticated && principal != null && !"anonymousUser".equals(principal)) {

                final Integer userId = Integer.valueOf(principal.toString());

                userLogin = new UserLogin(userId);
            }
        }

        return userLogin;
    }
}
