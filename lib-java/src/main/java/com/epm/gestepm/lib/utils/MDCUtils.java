package com.epm.gestepm.lib.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

public class MDCUtils {

    public static final String TRACE_ID_KEY = "X-B3-TraceId";

    public static final String USER_LOGIN_KEY = "userLogin";

    public static final String USER_PERMITS_KEY = "permits";

    private MDCUtils() {
    }

    public static String get(String key) {

        key = replaceInvalidGrafanaChars(key);

        return MDC.get(key);
    }

    public static void put(String key, String val) {

        key = replaceInvalidGrafanaChars(key);

        MDC.put(key, val);
    }

    public static String getTraceId() {
        return get(TRACE_ID_KEY);
    }

    public static String getUser() {
        return get(USER_LOGIN_KEY);
    }

    public static void putUser(String user) {
        put(USER_LOGIN_KEY, user);
    }

    public static List<String> getPermits() {

        String permits = get(USER_PERMITS_KEY);
        permits = permits != null ? permits : "";

        return Arrays.stream(permits.split(",")).map(String::trim).collect(Collectors.toList());
    }

    public static void putPermits(List<String> permits) {
        put(USER_PERMITS_KEY, String.join(", ", Objects.requireNonNullElse(permits, new ArrayList<>())));
    }

    public static void clear() {
        MDC.clear();
    }

    private static String replaceInvalidGrafanaChars(String text) {

        final String[] invalidCharsArray = new String[] { "-" };
        final String[] validCharsArray = new String[] { "_" };

        return StringUtils.replaceEach(text, invalidCharsArray, validCharsArray);
    }

}
