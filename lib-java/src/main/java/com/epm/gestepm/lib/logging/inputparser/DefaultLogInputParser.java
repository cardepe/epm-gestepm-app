package com.epm.gestepm.lib.logging.inputparser;

import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_INPUT;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class DefaultLogInputParser implements LogInputParser {

    @Override
    public Map<String, Object> parse(final String[] parameterNames, final Object[] args) {

        final Map<String, Object> params = new HashMap<>();

        for (int i = 0; i < parameterNames.length; i++) {

            final String parameterName = parameterNames[i];
            final String dataKey = String.format(LOG_CODE_TRACE_INPUT.concat(".%s"), parameterName);

            if (args[i] != null && MultipartFile.class.isAssignableFrom(args[i].getClass())) {

                params.put(dataKey, "multipart");

            } else if (args[i] != null && Resource.class.isAssignableFrom(args[i].getClass())) {

                params.put(dataKey, "resource");

            } else if (args[i] != null && args[i].getClass().equals(byte[].class)) {

                final int length = ((byte[]) args[i]).length;
                params.put(dataKey, String.format("byte[%d]", length));

            } else {
                params.put(dataKey, args[i]);
            }
        }

        return params;
    }

}
