package com.epm.gestepm.lib.testutils;

import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;

public class MockExecutionRequestProvider implements ExecutionRequestProvider {

    @Override
    public String getTraceId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRequestMethod() {
        return "UNKWN";
    }

    @Override
    public String getRequestPath() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveTraceId(final String traceId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveRequestMethod(final String method) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveRequestPath(final String path) {
        // TODO Auto-generated method stub

    }

}
