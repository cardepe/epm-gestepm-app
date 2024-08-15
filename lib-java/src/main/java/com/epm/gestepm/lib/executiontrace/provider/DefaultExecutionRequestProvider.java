package com.epm.gestepm.lib.executiontrace.provider;

import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.lib.utils.MDCUtils;

public class DefaultExecutionRequestProvider implements ExecutionRequestProvider {

    // private final RequestContext requestContext;

    // public DefaultExecutionRequestProvider(RequestContext requestContext) {
    //  this.requestContext = requestContext;
    // }

    public DefaultExecutionRequestProvider() { }

    @Override
    public String getTraceId() {
        return MDCUtils.getTraceId();
    }

    @Override
    public String getRequestMethod() {
        return "HTTP";
        //return requestContext.getType().name();
    }

    @Override
    public String getRequestPath() {
        return "PATH";
        // return requestContext.getPath();
    }

    @Override
    public void saveTraceId(String traceId) {
        // N/A
    }

    @Override
    public void saveRequestMethod(String method) {
        // N/A
    }

    @Override
    public void saveRequestPath(String path) {
        // N/A
    }

}
