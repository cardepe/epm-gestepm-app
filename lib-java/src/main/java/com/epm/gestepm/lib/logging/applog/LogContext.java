package com.epm.gestepm.lib.logging.applog;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LogContext {

    private final Map<String, Object> contextMap;

    public LogContext() {
        contextMap = new HashMap<>();
    }

    public LogContext put(final String key, final Object value) {

        if (key != null && value != null) {
            contextMap.put(key, value);
        }

        return this;
    }

    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    public Optional<Object> get(final String key) {
        return Optional.ofNullable(contextMap.get(key));
    }

    public Object getOrDefault(final String key, final Object defaultValue) {
        return contextMap.getOrDefault(key, defaultValue);
    }

    public <T> T getAs(final String key, Class<T> dataType) {
        return dataType.cast(contextMap.get(key));
    }

    public <R> R getAsOrDefault(String key, Class<R> dataType, R defaultValue) {
        return dataType.cast(getOrDefault(key, defaultValue));
    }

}
