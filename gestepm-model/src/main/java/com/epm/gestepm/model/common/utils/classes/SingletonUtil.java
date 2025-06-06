package com.epm.gestepm.model.common.utils.classes;

import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.role.service.RoleService;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Collections;
import java.util.List;

@Getter
@Named("singletonUtil")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SingletonUtil implements ServletContextListener {

	private final RoleService roleService;
	
	private List<Role> roles;

    public SingletonUtil(RoleService roleService) {
        this.roleService = roleService;
    }


    @Override
	public void contextInitialized(ServletContextEvent sce) {
		this.roles = roleService.getAll();
		Collections.reverse(this.roles);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Do not need it
	}
}
