package com.epm.gestepm.model.common.utils.security;

import com.epm.gestepm.lib.http.cookies.CookieUtils;
import com.epm.gestepm.model.common.utils.classes.SessionUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByEmailAndPasswordFinderDto;
import com.epm.gestepm.modelapi.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

public class SecurityAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final Log log = LogFactory.getLog(SecurityAuthenticationFilter.class);

	private final String GESTEPM_REMEMBER_LOGIN_INFO = "gestepm_remember_login_info";

	private final Integer COOKIE_MAX_AGE = 31536000;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionUtil sessionUtil;

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {

		int state = 0;
		
		final String email = request.getParameter("email");
		final String password = request.getParameter("password");
		final boolean remember = "on".equalsIgnoreCase(request.getParameter("remember"));

		try {
			
			if (!StringUtils.isAnyBlank(email, password)) {
				
				log.info("Iniciando sesión el usuario " + email);
				
				String passwordHash = Utiles.textToMD5(password);

				final UserDto user = this.userService.findOrNotFound(new UserByEmailAndPasswordFinderDto(email, passwordHash));

				if (user != null) {
		
					if (user.getState() == 0) {
						state = 1;
					} else {
					
						// Login y asignación de role en Spring Security
						UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getId(), password);
						authRequest.setDetails(user);
			
						Authentication autenticacion = this.getAuthenticationManager().authenticate(authRequest);
						SecurityContextHolder.getContext().setAuthentication(autenticacion);
						
						sessionUtil.setPassword(password);
	
						log.info("El usuario " + email + " ha iniciado sesión con éxito");

						if (remember) {

							final String encodedLoginData = Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
							CookieUtils.addCookie(GESTEPM_REMEMBER_LOGIN_INFO, encodedLoginData, COOKIE_MAX_AGE, response);

						} else {
							CookieUtils.removeCookie(GESTEPM_REMEMBER_LOGIN_INFO, response);
						}
	
						return autenticacion;
					}
				}
				
				log.info("Credenciales incorrectas por parte del usuario " + email);
			}

			response.sendRedirect("/login?error_type=" + state);
			
		} catch (Exception e) {
			log.error(e);
		}
		
		return null;
	}
}
