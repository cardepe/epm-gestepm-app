package com.epm.gestepm.lib.interceptor;

import com.epm.gestepm.lib.executiontrace.ThreadLocalExecutionTimeProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ExecutionTimeInterceptor implements HandlerInterceptor {

    private final ThreadLocalExecutionTimeProvider executionTimeProvider;

    public ExecutionTimeInterceptor(ThreadLocalExecutionTimeProvider executionTimeProvider) {
        this.executionTimeProvider = executionTimeProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        executionTimeProvider.acquire();
        executionTimeProvider.start();

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        executionTimeProvider.release();
    }

}
