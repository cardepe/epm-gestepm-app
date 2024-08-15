package com.epm.gestepm.config;

import com.epm.gestepm.lib.controller.restcontext.RestContextProvider;
import com.epm.gestepm.lib.user.data.UserLogin;
import com.epm.gestepm.lib.user.data.UserPermits;
import com.epm.gestepm.lib.user.data.resolver.UserLoginResolver;
import com.epm.gestepm.lib.user.data.resolver.UserPermitsResolver;
import com.epm.gestepm.lib.user.provider.DefaultUserProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * The App configuration.
 */
@Configuration
public class AppConfiguration {

  private static final String DS_NAME = "datasource";

  private static final String JDBC_TEMPLATE = "jdbc-template";

  @Primary
  @Bean(DS_NAME)
  @ConfigurationProperties(prefix = "data.jdbc.datasource.gestepmds")
  public DataSource datasource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(JDBC_TEMPLATE)
  public NamedParameterJdbcTemplate jdbcTemplate(@Qualifier(DS_NAME) DataSource primaryDS) {
    return new NamedParameterJdbcTemplate(primaryDS);
  }

  @Bean
  public DefaultUserProvider userProvider() {

    final DefaultUserProvider defaultUserProvider = new DefaultUserProvider();

    defaultUserProvider.register(UserLogin.class, new UserLoginResolver());
    defaultUserProvider.register(UserPermits.class, new UserPermitsResolver());

    return defaultUserProvider;
  }

  @Bean
  public RestContextProvider restContextProvider() {

    final RestContextProvider restContextProvider = new RestContextProvider();

    // TODO add operations

    return restContextProvider;
  }
}
