package com.epm.gestepm.lib.user.data.resolver;

import com.epm.gestepm.lib.user.data.UserPermits;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPermitsResolver implements UserDataResolver<UserPermits> {

  @Override
  public UserPermits resolve() {

    UserPermits userPermits = null;

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {

      final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
      final Boolean authenticated = authentication.isAuthenticated();

      if (authenticated) {

        final List<String> permits = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        userPermits = new UserPermits(permits);
      }
    }

    return userPermits;
  }

}
