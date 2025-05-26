package com.epm.gestepm.model.common.utils.security;

import com.epm.gestepm.modelapi.common.config.ApplicationContextProvider;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.model.common.utils.classes.SingletonUtil;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.userold.dto.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class SecurityAuthenticationManager implements AuthenticationManager  {

	private static final Log log = LogFactory.getLog(SecurityAuthenticationManager.class);
	
	public Authentication authenticate(Authentication authentication) {
		
		if (authentication.getPrincipal() == null) {
			log.info("No se ha introducido un nombre de usuario");
			throw new BadCredentialsException("Debe introducir un nombre de usuario");
		} else if (((String) authentication.getCredentials()).isEmpty()) {
			log.info("No se ha introducido una contraseña");
			throw new BadCredentialsException("Debe introducir una contraseña");
		}
		
		SingletonUtil singletonUtil = ApplicationContextProvider.getBean(SingletonUtil.class);
		
		User user = (User) authentication.getDetails();
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		// Las Authorities no son igual que Roles, pero sirve por ahora (en un futuro se podría mejorar)
		// 1) Administrador
		// 2) Administración
		// 3) RRHH
		// 4) Supervisor Técnico
		// 5) Jefe Proyecto
		// 6) Operario
		// 7) Oficina	
		// 8) Cliente
		
		if (user.getRole().getId() == Constants.ROLE_CUSTOMER_ID) {
			grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CUSTOMER));
		} else {
		
			List<Role> rolesReversed = singletonUtil.getRoles();

			boolean enabledBucle = false;
			
			for (Role role : rolesReversed) {
				
				if (role.getRoleName().equals(user.getRole().getRoleName())) {
					enabledBucle = true;
				}
				
				// Denegación de roles no piramidales
				if (Constants.ROLE_ADMINISTRATION_ID == user.getRole().getId()) {
					
					if (role.getId() == Constants.ROLE_RRHH_ID || role.getId() == Constants.ROLE_TECHNICAL_SUPERVISOR_ID || role.getId() == Constants.ROLE_PL_ID) {
						continue;
					}
					
				} else if (Constants.ROLE_RRHH_ID == user.getRole().getId()) {
					
					if (role.getId() == Constants.ROLE_TECHNICAL_SUPERVISOR_ID || role.getId() == Constants.ROLE_PL_ID) {
						continue;
					}
				}
				
				if (enabledBucle) {
					grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
				}
			}
		}

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getId(),
				user.getPassword(), grantedAuthorities);

		authRequest.setDetails(user);
		
		return authRequest;
	}
}
