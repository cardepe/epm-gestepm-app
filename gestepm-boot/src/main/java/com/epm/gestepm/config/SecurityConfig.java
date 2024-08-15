package com.epm.gestepm.config;

import com.epm.gestepm.model.common.utils.security.SecurityAuthenticationFilter;
import com.epm.gestepm.model.common.utils.security.SecurityAuthenticationManager;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity security) throws Exception {

		security
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/login*").permitAll()
				.antMatchers("/v1/**").permitAll() // TODO: change login and security
				.antMatchers("/shares**").hasAuthority(Constants.ROLE_PL)
				// .antMatchers("/users").hasAuthority(Constants.ROLE_PL)
				// .antMatchers("/users/**").hasAnyAuthority(Constants.ROLE_RRHH, Constants.ROLE_ADMINISTRATION)
				.antMatchers("/roles").hasAuthority(Constants.ROLE_ADMIN)
				.antMatchers("/roles/**").hasAuthority(Constants.ROLE_ADMIN)
			.anyRequest().authenticated()
			.and()
			.formLogin()
            	.loginPage("/login")
            	// .defaultSuccessUrl("/signing/personal", true)
            	.failureUrl("/login")
            .and()
			.addFilterBefore(authenticationFilter(), SecurityAuthenticationFilter.class)
			.logout().permitAll();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {

		web
			.ignoring().antMatchers("/ui/static/**") // css, font, images
				.and()
			.ignoring().antMatchers("/favicon.ico") // favicon
				.and()
			.ignoring().antMatchers("/webjars/**"); // jars de maven (bootstrap, jquery, fontawesome...)

	}
	
	@Bean
	public SecurityAuthenticationFilter authenticationFilter() {
		SecurityAuthenticationFilter authenticationFilter = new SecurityAuthenticationFilter();
		authenticationFilter.setAuthenticationManager(new SecurityAuthenticationManager());
		authenticationFilter.setFilterProcessesUrl("/login/check");
		return authenticationFilter;
	}
	
}
