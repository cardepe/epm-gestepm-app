package com.epm.gestepm.lib.interceptor;

import com.epm.gestepm.lib.executiontrace.ThreadLocalExecutionRequestProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ExecutionRequestInterceptor implements HandlerInterceptor {

    private final ThreadLocalExecutionRequestProvider requestProvider;

    public ExecutionRequestInterceptor(
            ThreadLocalExecutionRequestProvider requestProvider) {
        this.requestProvider = requestProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        requestProvider.acquire();

        final String method = request.getMethod();
        final String requestURI = request.getRequestURI();
        final String queryString = request.getQueryString();

        final String path = queryString != null ? requestURI.concat("?").concat(queryString) : requestURI;

        requestProvider.saveRequestMethod(method);
        requestProvider.saveRequestPath(path);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        requestProvider.release();
    }

}
