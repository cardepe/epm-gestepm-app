package com.epm.gestepm.lib.security.exception;

import java.util.List;

public class NotEnoughPermissionException extends RuntimeException {

    private final String action;

    private final List<String> requiredPermits;

    public NotEnoughPermissionException(String action, List<String> requiredPermits) {

        super(String.format("%s required permissions [%s] not met", action, String.join(",", requiredPermits)));

        this.action = action;
        this.requiredPermits = requiredPermits;
    }

    public String getAction() {
        return action;
    }

    public List<String> getRequiredPermits() {
        return requiredPermits;
    }

}
