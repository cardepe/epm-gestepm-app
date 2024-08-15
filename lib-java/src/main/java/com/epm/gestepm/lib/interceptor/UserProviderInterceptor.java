package com.epm.gestepm.lib.interceptor;

import java.util.Optional;
import com.epm.gestepm.lib.user.ThreadLocalUserProvider;
import com.epm.gestepm.lib.user.data.UserLogin;
import com.epm.gestepm.lib.user.data.UserPermits;
import com.epm.gestepm.lib.utils.MDCUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserProviderInterceptor implements HandlerInterceptor {

    private final ThreadLocalUserProvider userProvider;

    public UserProviderInterceptor(ThreadLocalUserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        userProvider.acquire();

        final Optional<UserLogin> userLogin = userProvider.get(UserLogin.class);
        userLogin.map(UserLogin::getValue).ifPresent(MDCUtils::putUser);

        final Optional<UserPermits> userPermits = userProvider.get(UserPermits.class);
        userPermits.map(UserPermits::getValue).ifPresent(MDCUtils::putPermits);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        userProvider.release();

        MDCUtils.clear();
    }

}
