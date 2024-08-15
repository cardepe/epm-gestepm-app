package com.epm.gestepm.lib.logging.outputparser;

import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_OUTPUT;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.core.io.Resource;

public class DefaultLogOutputParser implements LogOutputParser {

    @Override
    public Map<String, Object> parse(Object value) {

        final Map<String, Object> params = new HashMap<>();

        if (value == null) {

            params.put(LOG_CODE_TRACE_OUTPUT, "null/void");

        } else if (Optional.class.isAssignableFrom(value.getClass())) {

            if (((Optional<?>) value).isPresent()) {
                params.put(LOG_CODE_TRACE_OUTPUT, ((Optional<?>) value).get());
            } else {
                params.put(LOG_CODE_TRACE_OUTPUT, value);
            }

        } else if (Resource.class.isAssignableFrom(value.getClass())) {

            params.put(LOG_CODE_TRACE_OUTPUT, "resource");

        } else {
            params.put(LOG_CODE_TRACE_OUTPUT, value);
        }

        return params;
    }

    @Override
    public Map<String, Object> parse(List<Object> value) {

        final Map<String, Object> params = new HashMap<>();
        params.put(LOG_CODE_TRACE_OUTPUT, Objects.requireNonNullElse(value, "null/void"));

        return params;
    }

}
