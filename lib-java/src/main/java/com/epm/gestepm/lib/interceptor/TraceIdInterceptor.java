package com.epm.gestepm.lib.interceptor;

import com.epm.gestepm.lib.utils.MDCUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class TraceIdInterceptor implements HandlerInterceptor {

    private static final String TRACE_ID_HEADER_NAME = "TRID";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler) {

        response.setHeader(TRACE_ID_HEADER_NAME, MDCUtils.getTraceId());
        return true;
    }

}
