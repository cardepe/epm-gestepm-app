package com.epm.gestepm.lib.logging;

import java.lang.reflect.InvocationTargetException;
import org.slf4j.LoggerFactory;

public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static <T> T getInstance(final Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
            return null;
        }
    }

}
