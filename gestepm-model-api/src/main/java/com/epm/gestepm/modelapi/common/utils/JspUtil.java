package com.epm.gestepm.modelapi.common.utils;

import com.epm.gestepm.modelapi.common.config.ApplicationContextProvider;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;

public class JspUtil {
	
	public String parseTagToText(String tag) {
		
		MessageSource messageSource = ApplicationContextProvider.getBean(MessageSource.class);
		
		if (StringUtils.isNotBlank(tag)) {
			Locale locale = LocaleContextHolder.getLocale();
			return messageSource.getMessage(tag, new String[] { }, locale);
		}
		
		return "";
	}
	
	public Long getRolId(String name) {
		
		switch(name) {
		
			case Constants.ROLE_CUSTOMER:
				return Constants.ROLE_CUSTOMER_ID;
				
			case Constants.ROLE_OFFICE:
				return Constants.ROLE_OFFICE_ID;
				
			case Constants.ROLE_OPERATOR:
				return Constants.ROLE_OPERATOR_ID;
				
			case Constants.ROLE_PL:
				return Constants.ROLE_PL_ID;
				
			case Constants.ROLE_TECHNICAL_SUPERVISOR:
				return Constants.ROLE_TECHNICAL_SUPERVISOR_ID;
				
			case Constants.ROLE_RRHH:
				return Constants.ROLE_RRHH_ID;
				
			case Constants.ROLE_ADMINISTRATION:
				return Constants.ROLE_ADMINISTRATION_ID;
				
			case Constants.ROLE_ADMIN:
				return Constants.ROLE_ADMIN_ID;
		}
		
		return null;
	}
	
	public Boolean existsInList(List<Long> list, Long id) {
		return list.contains(id);
	}
}
