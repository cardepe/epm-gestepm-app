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
      final Boolean authenticated = authentication.isAuthenticated();

      if (authenticated && principal != null) {

        final String login = principal.toString();

        userLogin = new UserLogin(login);
      }
    }

    return userLogin;
  }

}
