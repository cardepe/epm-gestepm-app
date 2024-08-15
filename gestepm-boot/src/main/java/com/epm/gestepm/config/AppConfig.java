package com.epm.gestepm.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.epm.gestepm.model", entityManagerFactoryRef = "appEntityManager", transactionManagerRef = "appTransactionManager")
public class AppConfig {

	@Autowired
	private Environment env;

	@Primary
    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean appEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(appDataSource());
		em.setPackagesToScan(new String[] { "com.epm.gestepm.modelapi" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.enable_lazy_load_no_trans", "true"); // to work with jdbc/jpa
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Primary
	@Bean
	public PlatformTransactionManager appTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(appEntityManager().getObject());
		return transactionManager;
	}
}
