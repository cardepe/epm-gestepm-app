package com.epm.gestepm;

import com.epm.gestepm.lib.LibAutoConfiguration;
import com.epm.gestepm.lib.config.InterceptorConfiguration;
import com.epm.gestepm.lib.config.LocaleResolverConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties
@Import({LibAutoConfiguration.class, InterceptorConfiguration.class, LocaleResolverConfiguration.class})
public class Application extends SpringBootServletInitializer {

	/**
	 * Main.
	 * @param args the args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
