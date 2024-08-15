package com.epm.gestepm.lib.executiontrace;

import java.util.Objects;

public interface ExecutionRequestProvider {

    String getTraceId();

    String getRequestMethod();

    String getRequestPath();

    void saveTraceId(String traceId);

    void saveRequestMethod(String method);

    void saveRequestPath(String path);

    default String getRequestUri() {

        final String requestMethod = Objects.requireNonNullElse(getRequestMethod(), "UNKWN");
        final String requestPath = Objects.requireNonNullElse(getRequestPath(), "UNKWN");

        return String.format("%s %s", requestMethod, requestPath);
    }

}
