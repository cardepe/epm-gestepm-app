package com.epm.gestepm.lib.cache.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.core.env.PropertyResolver;

public class PropertyResolvingCacheResolver extends SimpleCacheResolver {

    private final PropertyResolver propertyResolver;

    public PropertyResolvingCacheResolver(final CacheManager cacheManager, final PropertyResolver propertyResolver) {
        super(cacheManager);
        this.propertyResolver = propertyResolver;
    }

    @Override
    protected Collection<String> getCacheNames(final CacheOperationInvocationContext<?> context) {

        Collection<String> unresolvedCacheNames = super.getCacheNames(context);
        unresolvedCacheNames = unresolvedCacheNames != null ? unresolvedCacheNames : new ArrayList<>();

        return unresolvedCacheNames.stream()
            .map(propertyResolver::resolveRequiredPlaceholders)
            .collect(Collectors.toList());
    }

}
