package com.epm.gestepm.lib.logging.inputparser;

import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_INPUT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public class DefaultLogInputParser implements LogInputParser {

    @Override
    public Map<String, Object> parse(final String[] parameterNames, final Object[] args) {
        final Map<String, Object> params = new HashMap<>();

        final Set<Class<?>> excludedTypes = Set.of(
                org.springframework.ui.Model.class,
                org.springframework.ui.ModelMap.class,
                javax.servlet.http.HttpServletRequest.class,
                javax.servlet.http.HttpServletResponse.class,
                java.util.Locale.class,
                org.springframework.validation.BindingResult.class
        );

        for (int i = 0; i < parameterNames.length; i++) {
            final String parameterName = parameterNames[i];
            final Object arg = args[i];
            final String dataKey = String.format("%s.%s", LOG_CODE_TRACE_INPUT, parameterName);

            if (arg == null) {
                params.put(dataKey, null);
                continue;
            }

            final Class<?> argClass = arg.getClass();

            if (excludedTypes.stream().anyMatch(type -> type.isAssignableFrom(argClass))) {
                continue;
            }

            if (MultipartFile.class.isAssignableFrom(argClass)) {
                params.put(dataKey, "multipart");
            } else if (Resource.class.isAssignableFrom(argClass)) {
                params.put(dataKey, "resource");
            } else if (byte[].class.equals(argClass)) {
                params.put(dataKey, String.format("byte[%d]", ((byte[]) arg).length));
            } else {
                params.put(dataKey, arg);
            }
        }

        return params;
    }

}
