package com.epm.gestepm.controller;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UnauthorizedController {

	private static final Log log = LogFactory.getLog(UnauthorizedController.class);

	@GetMapping("/unauthorized")
	public String unauthorizedMessage(@RequestParam(value = "error_type", required = false) Integer errorType, Locale locale, Model model) {

		log.info("Carga de la vista de autenticación (/unauthorized)");
		
		if (errorType != null) {
			model.addAttribute("errorType", errorType);
		}
		
		model.addAttribute("lang", locale.getLanguage());
		
		return "unauthorized";
	}
}
