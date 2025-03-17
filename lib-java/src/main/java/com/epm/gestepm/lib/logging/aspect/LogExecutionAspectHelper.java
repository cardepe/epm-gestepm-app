package com.epm.gestepm.lib.logging.aspect;

import static com.epm.gestepm.lib.logging.aspect.LogExecutionAspectUtils.getMethodAnnotation;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_METHOD_CACHE_EVICTIONS;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_METHOD_CACHE_INFO_IS_CACHED;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_METHOD_CACHE_INFO_IS_EVICT;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_METHOD_CACHE_INFO_KEY;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_METHOD_CACHE_NAME;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_ON_CLASS;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_ON_METHOD;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_PERMITS_ACTION;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_PERMITS_REQUIRED_ALL;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_PERMITS_REQUIRED_ONE_OF;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.epm.gestepm.lib.logging.ReflectionUtils;
import com.epm.gestepm.lib.logging.inputparser.LogInputParser;
import com.epm.gestepm.lib.logging.outputparser.LogOutputParser;
import com.epm.gestepm.lib.security.annotation.RequireOneOfPermits;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.PropertyResolver;

public class LogExecutionAspectHelper {

    private LogExecutionAspectHelper() {
    }

    public static String shortOutputData(Object result, boolean displayEnabled) {

        final String empty = ", result <empty>";
        final String oneItem = ", result <%s>";

        if (result instanceof Optional) {

            final Optional<?> optionalValue = (Optional<?>) result;

            return optionalValue.isPresent()
                    ? displayEnabled
                            ? optionalValue.get().toString()
                            : String.format(oneItem, optionalValue.get().getClass().getSimpleName())
                    : empty;

        } else if (result instanceof List) {

            if (displayEnabled) {
                return String.format(", result <List of size[%s]>", result);
            } else {
                final int listSize = ((List<?>) result).size();
                return String.format(", result <List of size[%d]>", listSize);
            }

        } else if ((result != null)
                && Arrays.asList(Boolean.class, Integer.class)
                    .contains(result.getClass())) {

            return String.format(oneItem, result);

        } else if (result instanceof byte[]) {

            int length = ((byte[]) result).length;
            return String.format(", result <byte[%d]>", length);

        } else {
            return result != null
                    ? displayEnabled
                        ? result.toString()
                        : String.format(oneItem, result.getClass().getSimpleName())
                    : empty;
        }
    }

    public static Map<String, Object> extractCacheInfo(JoinPoint joinPoint,
            ApplicationContext applicationContext) {

        Map<String, Object> joinPointCacheLogData = new HashMap<>();

        getMethodAnnotation(Cacheable.class, joinPoint).ifPresent(ann -> {

            final Method method = LogExecutionAspectUtils.getMethod(joinPoint);
            final String[] cacheName = ann.value();

            final PropertyResolver propResolver = applicationContext.getBean(PropertyResolver.class);

            final List<String> cacheNames = Arrays.stream(cacheName)
                .map(propResolver::resolveRequiredPlaceholders)
                .collect(Collectors.toList());

            joinPointCacheLogData.put(LOG_CODE_TRACE_METHOD_CACHE_INFO_IS_CACHED, true);
            joinPointCacheLogData.put(LOG_CODE_TRACE_METHOD_CACHE_NAME, String.join(",", cacheNames));

            final String keyGenerator = ann.keyGenerator();

            if (!keyGenerator.equals("")) {

                final KeyGenerator keyGen = applicationContext.getBean(keyGenerator, KeyGenerator.class);
                final Object key = keyGen.generate(null, method, joinPoint.getArgs());

                joinPointCacheLogData.put(LOG_CODE_TRACE_METHOD_CACHE_INFO_KEY, key);
            }
        });

        getMethodAnnotation(Caching.class, joinPoint).ifPresent(ann -> {

            final Map<String, String> evictionsMap = new HashMap<>();

            for (CacheEvict cacheEvict : ann.evict()) {

                joinPointCacheLogData.put(LOG_CODE_TRACE_METHOD_CACHE_INFO_IS_EVICT, true);

                final String[] cacheName = cacheEvict.value();
                final Method method = LogExecutionAspectUtils.getMethod(joinPoint);

                final PropertyResolver propResolver = applicationContext.getBean(PropertyResolver.class);

                final List<String> cacheNames = Arrays.stream(cacheName)
                    .map(propResolver::resolveRequiredPlaceholders)
                    .collect(Collectors.toList());

                final boolean allEntries = cacheEvict.allEntries();
                final String keyGenerator = cacheEvict.keyGenerator();

                if (!keyGenerator.equals("")) {

                    final KeyGenerator keyGen = applicationContext.getBean(keyGenerator, KeyGenerator.class);
                    final Object key = keyGen.generate(null, method, joinPoint.getArgs());

                    evictionsMap.put(String.join(",", cacheNames), key.toString());

                } else {
                    evictionsMap.put(String.join(",", cacheNames), String.format("allEntries[%s]", allEntries));
                }
            }

            joinPointCacheLogData.put(LOG_CODE_TRACE_METHOD_CACHE_EVICTIONS, evictionsMap);
        });

        return joinPointCacheLogData;
    }

    public static Map<String, Object> extractInput(Class<? extends LogInputParser> inputParserClass,
            JoinPoint joinPoint) {

        final HashMap<String, Object> inputMap = new HashMap<>();

        if (inputParserClass != null) {

            final LogInputParser parser = ReflectionUtils.getInstance(inputParserClass);

            if (parser != null) {

                final CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

                final String[] parameterNames = codeSignature.getParameterNames();
                final Object[] args = joinPoint.getArgs();
                final Map<String, Object> parsed = parser.parse(parameterNames, args);

                if (parsed != null) {
                    inputMap.putAll(parsed);
                }
            }
        }

        return inputMap;
    }

    public static Map<String, Object> parseOutput(Class<? extends LogOutputParser> outputParserClass, Object result) {

        final HashMap<String, Object> outputMap = new HashMap<>();

        if (outputParserClass != null) {

            final LogOutputParser parser = ReflectionUtils.getInstance(outputParserClass);

            if (parser != null) {

                final Map<String, Object> parsed = parser.parse(result);

                if (parsed != null) {
                    outputMap.putAll(parsed);
                }
            }
        }

        return outputMap;
    }

    public static Map<String, Object> extractCodeMarkers(JoinPoint joinPoint) {

        Map<String, Object> joinPointCodeTraceLogData = new HashMap<>();

        final String className = joinPoint.getTarget().getClass().getCanonicalName();
        final String methodName = joinPoint.getSignature().getName();

        joinPointCodeTraceLogData.put(LOG_CODE_TRACE_ON_CLASS, className);
        joinPointCodeTraceLogData.put(LOG_CODE_TRACE_ON_METHOD, methodName);

        return joinPointCodeTraceLogData;
    }

    public static Map<String, Object> extractPermissionInfo(JoinPoint joinPoint) {

        Map<String, Object> joinPointPermitLogData = new HashMap<>();

        getMethodAnnotation(RequirePermits.class, joinPoint).ifPresent(ann -> {

            final String action = ann.action();
            final String[] permits = ann.value();

            joinPointPermitLogData.put(LOG_PERMITS_ACTION, action);
            joinPointPermitLogData.put(LOG_PERMITS_REQUIRED_ALL, permits);

        });

        getMethodAnnotation(RequireOneOfPermits.class, joinPoint).ifPresent(ann -> {

            final String action = ann.action();
            final String[] permits = ann.value();

            joinPointPermitLogData.put(LOG_PERMITS_ACTION, action);
            joinPointPermitLogData.put(LOG_PERMITS_REQUIRED_ONE_OF, permits);

        });

        return joinPointPermitLogData;
    }

}
