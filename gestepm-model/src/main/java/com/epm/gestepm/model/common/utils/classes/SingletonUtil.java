package com.epm.gestepm.model.common.utils.classes;

import com.epm.gestepm.modelapi.absencetype.dto.AbsenceType;
import com.epm.gestepm.modelapi.absencetype.service.AbsenceTypeService;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.role.service.RoleService;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
@Named("singletonUtil")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SingletonUtil implements ServletContextListener {

	private final AbsenceTypeService absenceTypeService;

	private final RoleService roleService;

	private List<AbsenceType> absenceTypes;
	
	private List<Role> roles;

    public SingletonUtil(AbsenceTypeService absenceTypeService, RoleService roleService) {
        this.absenceTypeService = absenceTypeService;
        this.roleService = roleService;
    }


    @Override
	public void contextInitialized(ServletContextEvent sce) {
		this.absenceTypes = absenceTypeService.findAll();
		this.absenceTypes.sort(Comparator.comparing(AbsenceType::getName));
		
		this.roles = roleService.getAll();
		Collections.reverse(this.roles);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Do not need it
	}
	
	public AbsenceType getAbsenceTypeById(Long id) {
		return this.absenceTypes.stream()
                .filter(absenceType -> absenceType.getId().equals(id))
                .findAny()
                .orElse(null);
	}
}
