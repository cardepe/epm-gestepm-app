package com.epm.gestepm.lib.cache;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.springframework.cache.interceptor.KeyGenerator;

public class DefaultCacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(final Object target, final Method method, final Object... params) {

        final String returnTypeName = method.getReturnType().getSimpleName();

        final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();
        cacheKeyBuilder.addElement("type", returnTypeName);

        final Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {

            final Object object = params[i];
            final String parameterName = parameters[i].getName();

            if ((object != null) && UsableAsCacheKey.class.isAssignableFrom(object.getClass())) {

                cacheKeyBuilder.addElement(((UsableAsCacheKey) object).asCacheKey());

            } else {

                cacheKeyBuilder.addElement(parameterName, object);
            }
        }

        return cacheKeyBuilder.toString();

    }

}
