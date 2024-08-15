package com.epm.gestepm.lib;

import com.epm.gestepm.lib.applocale.model.dao.AppLocaleDaoImpl;
import com.epm.gestepm.lib.applocale.model.service.AppLocaleServiceImpl;
import com.epm.gestepm.lib.audit.AuditProvider;
import com.epm.gestepm.lib.audit.DefaultAuditProvider;
import com.epm.gestepm.lib.cache.DefaultCacheKeyGenerator;
import com.epm.gestepm.lib.cache.config.PropertyResolvingCacheResolver;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.executiontrace.ExecutionTimeProvider;
import com.epm.gestepm.lib.executiontrace.provider.DefaultExecutionRequestProvider;
import com.epm.gestepm.lib.executiontrace.provider.DefaultExecutionTimeProvider;
import com.epm.gestepm.lib.jdbc.impl.datasource.DefaultSQLDatasource;
import com.epm.gestepm.lib.jdbc.impl.keyholder.DefaultSQLKeyHolderGenerator;
import com.epm.gestepm.lib.jdbc.impl.statement.DefaultSQLStatementBuilder;
import com.epm.gestepm.lib.jdbc.impl.statement.DefaultSQLStatementKeyResolver;
import com.epm.gestepm.lib.locale.provider.DefaultLocaleProvider;
import com.epm.gestepm.lib.logging.aspect.LogExecutionAspect;
import com.epm.gestepm.lib.security.PermissionCheckAspect;
import com.epm.gestepm.lib.user.UserProvider;
import com.epm.gestepm.lib.user.UserProviderContext;
import com.epm.gestepm.lib.user.aspect.UserProviderAspect;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertyResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ImportAutoConfiguration(classes = {DefaultSQLKeyHolderGenerator.class, DefaultSQLStatementKeyResolver.class,
        DefaultSQLStatementBuilder.class, DefaultSQLDatasource.class, DefaultExecutionRequestProvider.class,
        DefaultExecutionTimeProvider.class, DefaultLocaleProvider.class,
        LogExecutionAspect.class, PermissionCheckAspect.class, AppLocaleServiceImpl.class, AppLocaleDaoImpl.class,
        I18nErrorMessageSource.class, ResponseSuccessfulHelper.class, UserProviderAspect.class,
        UserProviderContext.class
})
public class LibAutoConfiguration {

  @Bean("keyStatementMap")
  @ConfigurationProperties(prefix = "sql")
  public Map<String, String> keyStatementMap() {
    return new HashMap<>();
  }

  @Bean
  public DefaultCacheKeyGenerator defaultCacheKeyGenerator() {
    return new DefaultCacheKeyGenerator();
  }

  @Bean
  public PropertyResolvingCacheResolver defaultCacheResolver(CacheManager cacheManager,
                                                             PropertyResolver propertyResolver) {
    return new PropertyResolvingCacheResolver(cacheManager, propertyResolver);
  }

  @Bean
  public AuditProvider auditProvider(UserProvider userProvider, ExecutionTimeProvider executionTimeProvider) {
    return new DefaultAuditProvider(userProvider, executionTimeProvider);
  }

}
